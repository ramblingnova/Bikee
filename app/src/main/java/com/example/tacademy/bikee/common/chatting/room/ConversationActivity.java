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

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
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

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConversationActivity extends AppCompatActivity {
    @Bind(R.id.conversation_toolbar_target_user_name_text_view)
    TextView targetUserName;
    @Bind(R.id.activity_conversation_bicycle_image_image_view)
    ImageView bicycleImage;
    @Bind(R.id.activity_conversation_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.activity_conversation_reservation_period_text_view)
    TextView reservationPeriod;
    @Bind(R.id.activity_conversation_reservation_state_image_view)
    ImageView reservationStateImage;
    @Bind(R.id.activity_conversation_reservation_state_text_view)
    TextView reservationState;
    @Bind(R.id.activity_conversation_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.activity_conversation_writing_bar_layout)
    RelativeLayout writingBar;
    @Bind(R.id.activity_conversation_writing_bar_message_edit_text)
    EditText messageEditText;
    @Bind(R.id.activity_conversation_writing_bar_send_message_button)
    Button sendMessage;

    private ConversationAdapter conversationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean lastVisibleItem;
    private SoftKeyboardDetectorView softKeyboardDetector;
    private String messageChannelURL;
    private MessagingChannel mMessagingChannel;
    private CountDownTimer mTimer;

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
        targetUserName.setText(intent.getStringExtra("TARGET_USER_NAME"));
        String appId = intent.getStringExtra("APP_ID");
        String userId = intent.getStringExtra("USER_ID");
        String myName = intent.getStringExtra("USER_NAME");
        // TODO : SharedPreperence에서 찾아와야 한다.
        String gcmRegToken = "f7x_1qavNuM:APA91bGB8RVUTMtxFbTehOYO-gr5JFUORJQZDLtzAsXoDD_o2ZBqHn_PhqAfzpJwSbY6SF6iY7_mfK4nrEERZsZbq5HuddaVqKPBA6OKBdjJrSTxjEJEyfIzLcJeNpPcgoo0f66cXwxY";
        messageChannelURL = intent.getStringExtra("messageChannelUrl");

        messageEditText.addTextChangedListener(tw);

        SendBird.init(this, appId);
        SendBird.login(SendBird.LoginOption.build(userId).setUserName(myName).setGCMRegToken(gcmRegToken));

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
                updateReadStatus(messagingChannel);
                SendBird.queryMessageList(messagingChannel.getUrl()).load(
                        Long.MAX_VALUE,
                        30,
                        10,
                        new MessageListQuery.MessageListQueryResult() {
                    @Override
                    public void onResult(List<MessageModel> messageModels) {
                        Log.i(TAG, "onMessagingStarted onResult");
                        for (MessageModel model : messageModels) {

                            conversationAdapter.add(
                                    new ConversationItem(
                                            model,
                                            new Date(model.getTimestamp()),
                                            ((Message) model).getSenderId().equals(SendBird.getUserId()) ? ConversationAdapter.SEND : ConversationAdapter.RECEIVE
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
                                if (messageModels.size() <= 0) {
                                    return;
                                }

                                int completeVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                                for (MessageModel model : messageModels)
                                    conversationAdapter.add(
                                            new ConversationItem(
                                                    model,
                                                    new Date(model.getTimestamp()),
                                                    ((Message) model).getSenderId().equals(SendBird.getUserId()) ? ConversationAdapter.SEND : ConversationAdapter.RECEIVE
                                            )
                                    );
                                conversationAdapter.notifyDataSetChanged();

                                recyclerView.scrollToPosition(messageModels.size() - 1 + completeVisiblePosition + 1);
                            }

                            @Override
                            public void onError(Exception e) {

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
                            if (messageModels.size() <= 0) {
                                return;
                            }

                            for (MessageModel model : messageModels)
                                conversationAdapter.add(
                                        new ConversationItem(
                                                model,
                                                new Date(model.getTimestamp()),
                                                ((Message) model).getSenderId().equals(SendBird.getUserId()) ? ConversationAdapter.SEND : ConversationAdapter.RECEIVE
                                        )
                                );
                            conversationAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
            }
        });

        softKeyboardDetector = new SoftKeyboardDetectorView(this);
        addContentView(softKeyboardDetector, new FrameLayout.LayoutParams(-1, -1));
        softKeyboardDetector.setOnShownKeyboard(new SoftKeyboardDetectorView.OnShownKeyboardListener() {
            @Override
            public void onShowSoftKeyboard() {
                if (lastVisibleItem)
                    recyclerView.scrollToPosition(conversationAdapter.getItemCount() - 1);
            }
        });
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
            sendMessage.setEnabled(s.length() > 0);

            if (s.length() > 0) {
                SendBird.typeStart();
            } else {
                SendBird.typeEnd();
            }
        }
    };

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
            SendBird.joinMessaging(messageChannelURL);
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

    @OnClick(R.id.conversation_toolbar_back_button_layout)
    void back(View view) {
        super.onBackPressed();
    }

    @OnClick(R.id.activity_conversation_writing_bar_send_message_button)
    void sendMessage(View view) {

        SendBird.send(messageEditText.getText().toString());
        /*conversationAdapter.add(new ConversationItem(
                "aaa",
                "" + (++i),
                new Date(),
                ConversationAdapter.SEND
        ));
        recyclerView.scrollToPosition(conversationAdapter.getItemCount()-1);*/
        messageEditText.setText("");
        sendMessage.setEnabled(false);
    }

    private void init() {
        ImageUtil.setRoundRectangleImageFromURL(
                this,
                "",
                R.drawable.detailpage_bike_image_noneimage,
                bicycleImage,
                getResources().getDimensionPixelOffset(R.dimen.activity_conversation_bicycle_image_image_view_radius)
        );
        bicycleName.setText("2015 아메리칸이글 CY 픽시 블랙");
        reservationPeriod.setText("10.25 17:00 ~ 10.26 19:00");
        reservationStateImage.setImageResource(R.drawable.icon_step3);
        reservationState.setText("예약승인");
        if (Build.VERSION.SDK_INT < 23)
            reservationState.setTextColor(getResources().getColor(R.color.bikeeBlue));
        else
            reservationState.setTextColor(getResources().getColor(R.color.bikeeBlue, null));
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
