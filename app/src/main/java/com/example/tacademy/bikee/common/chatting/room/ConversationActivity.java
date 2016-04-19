package com.example.tacademy.bikee.common.chatting.room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.GetChannelResInfoReceiveObject;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.SendBirdSendObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;
import com.sendbird.android.MessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdEventHandler;
import com.sendbird.android.SendBirdNotificationHandler;
import com.sendbird.android.model.BroadcastMessage;
import com.sendbird.android.model.Channel;
import com.sendbird.android.model.FileLink;
import com.sendbird.android.model.Mention;
import com.sendbird.android.model.Message;
import com.sendbird.android.model.MessageModel;
import com.sendbird.android.model.MessagingChannel;
import com.sendbird.android.model.ReadStatus;
import com.sendbird.android.model.SystemMessage;
import com.sendbird.android.model.TypeStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {
    @Bind(R.id.conversation_toolbar_target_user_name_text_view)
    TextView targetUserNameTextView;
    @Bind(R.id.activity_conversation_bicycle_image_image_view)
    ImageView bicycleImageImageView;
    @Bind(R.id.activity_conversation_bicycle_name_text_view)
    TextView bicycleNameTextView;
    @Bind(R.id.activity_conversation_clock_image_image_view)
    ImageView clockImageImageView;
    @Bind(R.id.activity_conversation_reservation_period_text_view)
    TextView reservationPeriodTextView;
    @Bind(R.id.activity_conversation_reservation_state_image_view)
    ImageView reservationStateImageImageView;
    @Bind(R.id.activity_conversation_reservation_state_text_view)
    TextView reservationStateTextView;
    @Bind(R.id.activity_conversation_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.activity_conversation_writing_bar_layout)
    RelativeLayout writingBar;
    @Bind(R.id.activity_conversation_writing_bar_message_edit_text)
    EditText messageEditText;
    @Bind(R.id.activity_conversation_writing_bar_send_message_button)
    Button sendMessageButton;

    private ConversationAdapter conversationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean lastVisibleItem;
    private SoftKeyboardDetectorView softKeyboardDetector;
    private MessagingChannel mMessagingChannel;
    private CountDownTimer mTimer;

    private String userId;
    private String targetUserId;
    private String targetUserName;
    private String bicycleId;
    private String bicycleName;
    private String channelUrl;

    private static final String TAG = "CONVERSATION_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_conversation_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.conversation_toolbar);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("USER_ID");
        targetUserId = intent.getStringExtra("TARGET_USER_ID");
        targetUserName = intent.getStringExtra("TARGET_USER_NAME");
        bicycleId = intent.getStringExtra("BICYCLE_ID");
        bicycleName = intent.getStringExtra("BICYCLE_NAME");
        channelUrl = intent.getStringExtra("CHANNEL_URL");

        messageEditText.addTextChangedListener(tw);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        conversationAdapter = new ConversationAdapter();
        init();
        recyclerView.setAdapter(conversationAdapter);

        recyclerView.addItemDecoration(
                new ConversationDecoration(
                        getResources().getDimensionPixelSize(R.dimen.view_holder_conversation_item_bottom_space)
                )
        );

        lastVisibleItem = true;

        softKeyboardDetector = new SoftKeyboardDetectorView(this);
        addContentView(softKeyboardDetector, new FrameLayout.LayoutParams(-1, -1));
        softKeyboardDetector.setOnShownKeyboard(new SoftKeyboardDetectorView.OnShownKeyboardListener() {
            @Override
            public void onShowSoftKeyboard() {
                if (lastVisibleItem)
                    recyclerView.scrollToPosition(conversationAdapter.getItemCount() - 1);
            }
        });

        SendBird.registerNotificationHandler(new SendBirdNotificationHandler() {
            @Override
            public void onMessagingChannelUpdated(MessagingChannel messagingChannel) {
                if ((mMessagingChannel != null)
                        && (mMessagingChannel.getId() == messagingChannel.getId()))
                    updateReadStatus(messagingChannel);
            }

            @Override
            public void onMentionUpdated(Mention mention) {

            }
        });

        SendBird.setEventHandler(new SendBirdEventHandler() {
            @Override
            public void onConnect(Channel channel) {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onChannelLeft(Channel channel) {

            }

            @Override
            public void onMessageReceived(Message message) {
                SendBird.markAsRead();
                conversationAdapter.add(
                        new ConversationItem(
                                message,
                                new Date(message.getTimestamp()),
                                message.getSenderId().equals(SendBird.getUserId()) ? ConversationAdapter.SEND : ConversationAdapter.RECEIVE
                        )
                );
                conversationAdapter.notifyDataSetChanged();

                recyclerView.scrollToPosition(conversationAdapter.getItemCount() - 1);
            }

            @Override
            public void onMutedMessageReceived(Message message) {

            }

            @Override
            public void onSystemMessageReceived(SystemMessage systemMessage) {

            }

            @Override
            public void onBroadcastMessageReceived(BroadcastMessage broadcastMessage) {

            }

            @Override
            public void onFileReceived(FileLink fileLink) {

            }

            @Override
            public void onMutedFileReceived(FileLink fileLink) {

            }

            @Override
            public void onReadReceived(ReadStatus readStatus) {
                conversationAdapter.setReadStatus(readStatus.getUserId(), readStatus.getTimestamp());
            }

            @Override
            public void onTypeStartReceived(TypeStatus typeStatus) {

            }

            @Override
            public void onTypeEndReceived(TypeStatus typeStatus) {

            }

            @Override
            public void onAllDataReceived(SendBird.SendBirdDataType sendBirdDataType, int i) {

            }

            @Override
            public void onMessageDelivery(boolean b, String s, String s1, String s2) {

            }

            @Override
            public void onMessagingStarted(final MessagingChannel messagingChannel) {
                if (getIntent().getBooleanExtra("START", false)) {
                    SendBirdSendObject sendBirdSendObject = new SendBirdSendObject();
                    sendBirdSendObject.setRenter(userId);
                    sendBirdSendObject.setLister(targetUserId);
                    sendBirdSendObject.setChannel_url(messagingChannel.getUrl());
                    sendBirdSendObject.setBike(bicycleId);

                    NetworkManager.getInstance().createChannel(
                            sendBirdSendObject,
                            null,
                            new Callback<ReceiveObject>() {
                                @Override
                                public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                    if (BuildConfig.DEBUG)
                                        Log.d(TAG, "createChannel onResponse");
                                }

                                @Override
                                public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                    if (BuildConfig.DEBUG)
                                        Log.d(TAG, "createChannel onFailure Error : " + t.toString());
                                }
                            });
                }

                updateReadStatus(messagingChannel);

                SendBird.queryMessageList(messagingChannel.getUrl()).load(
                        Long.MAX_VALUE,
                        30,
                        10,
                        new MessageListQuery.MessageListQueryResult() {
                            @Override
                            public void onResult(List<MessageModel> messageModels) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "onMessagingStarted onResult");

                                for (MessageModel model : messageModels) {
                                    conversationAdapter.add(
                                            new ConversationItem(
                                                    model,
                                                    new Date(model.getTimestamp()),
                                                    ((Message) model).getSenderId().equals(SendBird.getUserId())
                                                            ? ConversationAdapter.SEND
                                                            : ConversationAdapter.RECEIVE
                                            )
                                    );
                                }

                                conversationAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(conversationAdapter.getItemCount() - 1);

                                SendBird.markAsRead(messagingChannel.getUrl());
                                SendBird.join(messagingChannel.getUrl());
                                SendBird.connect(conversationAdapter.getMaxMessageTimestamp());
                            }

                            @Override
                            public void onError(Exception e) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "onMessagingStarted onError", e);
                            }
                        });
            }

            @Override
            public void onMessagingUpdated(MessagingChannel messagingChannel) {
                updateReadStatus(messagingChannel);
            }

            @Override
            public void onMessagingEnded(MessagingChannel messagingChannel) {

            }

            @Override
            public void onAllMessagingEnded() {

            }

            @Override
            public void onMessagingHidden(MessagingChannel messagingChannel) {

            }

            @Override
            public void onAllMessagingHidden() {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = conversationAdapter.getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                lastVisibleItem = ((totalItemCount - 1) == lastVisibleItemPosition);
            }

            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);

                if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (linearLayoutManager.findFirstVisibleItemPosition() == 0
                            && recyclerView.getChildCount() > 0) {
                        SendBird.queryMessageList(SendBird.getChannelUrl()).prev(
                                conversationAdapter.getMinMessageTimestamp(),
                                30,
                                new MessageListQuery.MessageListQueryResult() {
                                    @Override
                                    public void onResult(List<MessageModel> messageModels) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "onScrollStateChanged onResult");

                                        if (messageModels.size() <= 0) {
                                            return;
                                        }

                                        int completeVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                                        for (MessageModel model : messageModels)
                                            conversationAdapter.add(
                                                    new ConversationItem(
                                                            model,
                                                            new Date(model.getTimestamp()),
                                                            ((Message) model).getSenderId().equals(SendBird.getUserId())
                                                                    ? ConversationAdapter.SEND
                                                                    : ConversationAdapter.RECEIVE
                                                    )
                                            );
                                        conversationAdapter.notifyDataSetChanged();

                                        // TODO : 정말 필요한 것인 지 생각해볼 것
                                        recyclerView.scrollToPosition(messageModels.size() - 1 + completeVisiblePosition + 1);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "onScrollStateChanged onError", e);
                                    }
                                });
                    }
                } else if (linearLayoutManager.findLastVisibleItemPosition() == conversationAdapter.getItemCount() - 1
                        && recyclerView.getChildCount() > 0) {
                    SendBird.queryMessageList(SendBird.getChannelUrl()).next(
                            conversationAdapter.getMaxMessageTimestamp(),
                            30,
                            new MessageListQuery.MessageListQueryResult() {
                                @Override
                                public void onResult(List<MessageModel> messageModels) {
                                    if (BuildConfig.DEBUG)
                                        Log.d(TAG, "onScrollStateChanged onResult");

                                    if (messageModels.size() <= 0) {
                                        return;
                                    }

                                    for (MessageModel model : messageModels)
                                        conversationAdapter.add(
                                                new ConversationItem(
                                                        model,
                                                        new Date(model.getTimestamp()),
                                                        ((Message) model).getSenderId().equals(SendBird.getUserId())
                                                                ? ConversationAdapter.SEND
                                                                : ConversationAdapter.RECEIVE
                                                )
                                        );
                                    conversationAdapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onError(Exception e) {
                                    if (BuildConfig.DEBUG)
                                        Log.d(TAG, "onScrollStateChanged onError", e);
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SendBird.markAsRead();

        if (mTimer != null)
            mTimer.cancel();
        mTimer = new CountDownTimer(60 * 60 * 24 * 7 * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (conversationAdapter != null)
                    conversationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinish() {

            }
        };
        mTimer.start();

        conversationAdapter.clear();
        conversationAdapter.notifyDataSetChanged();

        if (getIntent().getBooleanExtra("JOIN", false)) {
            SendBird.joinMessaging(channelUrl);
        } else if (getIntent().getBooleanExtra("START", false)) {
            SendBird.startMessaging(getIntent().getStringExtra("TARGET_USER_ID"));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mTimer != null)
            mTimer.cancel();

        SendBird.disconnect();
    }

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            sendMessageButton.setEnabled(s.length() > 0);

            if (s.length() > 0) {
                SendBird.typeStart();
            } else {
                SendBird.typeEnd();
            }
        }
    };

    @OnClick(R.id.conversation_toolbar_back_button_layout)
    void back(View view) {
        super.onBackPressed();
    }

    @OnClick(R.id.activity_conversation_writing_bar_send_message_button)
    void sendMessage(View view) {
        SendBird.send(messageEditText.getText().toString());

        messageEditText.setText("");
        sendMessageButton.setEnabled(false);
    }

    private void init() {
        targetUserNameTextView.setText(targetUserName);

        SendBirdSendObject sendBirdSendObject = new SendBirdSendObject();
        sendBirdSendObject.setRenter(userId);
        sendBirdSendObject.setLister(targetUserId);
        sendBirdSendObject.setBike(bicycleId);

        NetworkManager.getInstance().getChannelResInfo(
                sendBirdSendObject,
                null,
                new Callback<GetChannelResInfoReceiveObject>() {
                    @Override
                    public void onResponse(Call<GetChannelResInfoReceiveObject> call, Response<GetChannelResInfoReceiveObject> response) {
                        GetChannelResInfoReceiveObject receiveObject = response.body();
                        if (receiveObject.getResult().size() == 0) {
                            NetworkManager.getInstance().selectBicycleDetail(
                                    bicycleId,
                                    null,
                                    new Callback<ReceiveObject>() {
                                        @Override
                                        public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "selectBicycleDetail onResponse");

                                            ReceiveObject receiveObject = response.body();

                                            bicycleNameTextView.setText(receiveObject.getResult().get(0).getTitle());
                                            ImageUtil.setRoundRectangleImageFromURL(
                                                    ConversationActivity.this,
                                                    RefinementUtil.getBicycleImageURLStringFromResult(receiveObject.getResult().get(0)),
                                                    R.drawable.detailpage_bike_image_noneimage,
                                                    bicycleImageImageView,
                                                    getResources().getDimensionPixelOffset(R.dimen.activity_conversation_bicycle_image_image_view_radius)
                                            );
                                            clockImageImageView.setVisibility(View.INVISIBLE);
                                            reservationPeriodTextView.setVisibility(View.INVISIBLE);
                                            reservationStateImageImageView.setVisibility(View.INVISIBLE);
                                            reservationStateTextView.setVisibility(View.INVISIBLE);
                                        }

                                        @Override
                                        public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "selectBicycleDetail onFailure", t);
                                        }
                                    });
                        } else {
                            bicycleNameTextView.setText(receiveObject.getResult().get(0).getBike().getTitle());
                            ImageUtil.setRoundRectangleImageFromURL(
                                    ConversationActivity.this,
                                    RefinementUtil.getBicycleImageURLStringFromSendBirdResult(receiveObject.getResult().get(0)),
                                    R.drawable.detailpage_bike_image_noneimage,
                                    bicycleImageImageView,
                                    getResources().getDimensionPixelOffset(R.dimen.activity_conversation_bicycle_image_image_view_radius)
                            );
                            clockImageImageView.setVisibility(View.VISIBLE);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd hh:mm", Locale.getDefault());
                            String reservationPeriod = simpleDateFormat.format(receiveObject.getResult().get(0).getReserve().getRentStart())
                                    + " ~ "
                                    + simpleDateFormat.format(receiveObject.getResult().get(0).getReserve().getRentEnd());
                            reservationPeriodTextView.setText(reservationPeriod);
                            // TODO : 예약 상태에 따라 아이콘과 텍스트 컬러가 바뀜
                            switch (receiveObject.getResult().get(0).getReserve().getStatus()) {
                                case "RR":
                                    reservationStateImageImageView.setImageResource(R.drawable.icon_step1);
                                    reservationStateTextView.setText("예약승인");
                                    if (Build.VERSION.SDK_INT < 23)
                                        reservationStateTextView.setTextColor(getResources().getColor(R.color.bikeeYellow));
                                    else
                                        reservationStateTextView.setTextColor(getResources().getColor(R.color.bikeeYellow, null));
                                    break;
                                case "RS":
                                    reservationStateImageImageView.setImageResource(R.drawable.icon_step2);
                                    reservationStateTextView.setText("예약승인");
                                    if (Build.VERSION.SDK_INT < 23)
                                        reservationStateTextView.setTextColor(getResources().getColor(R.color.bikeeRed));
                                    else
                                        reservationStateTextView.setTextColor(getResources().getColor(R.color.bikeeRed, null));
                                    break;
                                case "RC":
                                    reservationStateImageImageView.setImageResource(R.drawable.icon_step3);
                                    reservationStateTextView.setText("예약승인");
                                    if (Build.VERSION.SDK_INT < 23)
                                        reservationStateTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
                                    else
                                        reservationStateTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, null));
                                    break;
                                default:
                                    clockImageImageView.setVisibility(View.INVISIBLE);
                                    reservationPeriodTextView.setVisibility(View.INVISIBLE);
                                    reservationStateImageImageView.setVisibility(View.INVISIBLE);
                                    reservationStateTextView.setVisibility(View.INVISIBLE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetChannelResInfoReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "getChannelResInfo onFailure Error : " + t.toString());
                    }
                });
    }

    private void updateReadStatus(MessagingChannel messagingChannel) {
        mMessagingChannel = messagingChannel;
        Hashtable<String, Long> readStatus = new Hashtable<>();

        for (MessagingChannel.Member member : messagingChannel.getMembers()) {
            Long currentStatus = conversationAdapter.getReadStatusTable().get(member.getId());
            if (currentStatus == null)
                currentStatus = 0L;
            readStatus.put(member.getId(), Math.max(currentStatus, messagingChannel.getLastReadMillis(member.getId())));
        }

        conversationAdapter.resetReadStatusTable(readStatus);
        conversationAdapter.notifyDataSetChanged();
    }
}
