package com.example.tacademy.bikee.common.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.chatting.room.ConversationActivity;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.SendBirdSendObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.sendbird.android.MessagingChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdNotificationHandler;
import com.sendbird.android.model.Mention;
import com.sendbird.android.model.MessagingChannel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomsFragment extends Fragment implements OnChattingRoomAdapterClickListener {
    @Bind(R.id.fragment_chatting_rooms_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private MessagingChannelListQuery mMessagingChannelListQuery;
    private ChattingRoomAdapter chattingRoomAdapter;
    private String appId;
    private String userId;
    private String userName;

    private static final String TAG = "CHATTING_ROOMS_FRAGMENT";

    public static ChattingRoomsFragment newInstance(String appId, String userId, String userName, String gcmRegToken) {
        ChattingRoomsFragment chattingRoomsFragment = new ChattingRoomsFragment();
        Bundle args = new Bundle();
        args.putString("APP_ID", appId);
        args.putString("USER_ID", userId);
        args.putString("USER_NAME", userName);
        args.putString("GCM_TOKEN", gcmRegToken);
        chattingRoomsFragment.setArguments(args);

        return chattingRoomsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting_rooms, container, false);

        ButterKnife.bind(this, view);

        appId = "2E377FE1-E1AD-4484-A66F-696AF1306F58";
        userId = PropertyManager.getInstance().get_id();
        userName = PropertyManager.getInstance().getName();

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        chattingRoomAdapter = new ChattingRoomAdapter();
        chattingRoomAdapter.setOnChattingRoomAdapterClickListener(this);
        recyclerView.setAdapter(chattingRoomAdapter);

        recyclerView.addItemDecoration(
                new ChattingRoomDecoration(
                        getResources().getDimensionPixelSize(R.dimen.view_holder_chatting_room_item_space)
                )
        );

        return view;
    }

    String gcmRegToken;

    @Override
    public void onResume() {
        super.onResume();

        if (PropertyManager.getInstance().getSignInState() != PropertyManager.SIGN_OUT_STATE) {
            // TODO : SENDBIRD
            userId = PropertyManager.getInstance().get_id();
            userName = PropertyManager.getInstance().getName();
            gcmRegToken = PropertyManager.getInstance().getGCMToken();

            SendBird.init(getContext(), appId);
            SendBird.login(SendBird.LoginOption.build(userId).setUserName(userName).setGCMRegToken(gcmRegToken));

            SendBird.registerNotificationHandler(new SendBirdNotificationHandler() {
                @Override
                public void onMessagingChannelUpdated(MessagingChannel messagingChannel) {
                    chattingRoomAdapter.replace(messagingChannel);
                }

                @Override
                public void onMentionUpdated(Mention mention) {

                }
            });

            recyclerView.addOnScrollListener(onScrollListener);

            if (mMessagingChannelListQuery == null) {
                mMessagingChannelListQuery = SendBird.queryMessagingChannelList();
                mMessagingChannelListQuery.setLimit(30);
            }

            mMessagingChannelListQuery.next(new MessagingChannelListQuery.MessagingChannelListQueryResult() {
                @Override
                public void onResult(List<MessagingChannel> list) {
                    chattingRoomAdapter.clear();

                    String reservationState = null;
                    int i = 0;

                    for (MessagingChannel messagingChannel : list) {
                        // TODO : 채팅방 예약 정보 들고오기
                        SendBirdSendObject sendBirdSendObject = new SendBirdSendObject();
                        sendBirdSendObject.setRenter(userId);
                        for (MessagingChannel.Member member : messagingChannel.getMembers())
                            if (!member.getId().equals(userId))
                                sendBirdSendObject.setLister(member.getId());
                        sendBirdSendObject.setChannel_url(messagingChannel.getUrl());
                        NetworkManager.getInstance().getChannelResInfo(
                                sendBirdSendObject,
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "createChannel success");
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "onFailure Error : " + t.toString());
                                    }
                                });
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "channelUrl : " + messagingChannel.getUrl());
                        switch (++i % 3) {
                            case 0:
                                reservationState = "RR";
                                break;
                            case 1:
                                reservationState = "RS";
                                break;
                            case 2:
                                reservationState = "RC";
                                break;
                        }

                        chattingRoomAdapter.add(
                                new ChattingRoomItem(
                                        messagingChannel,
                                        reservationState,
                                        "Bicycle Name" + i
                                )
                        );
                    }

                    SendBird.join("");
                    SendBird.connect();
                }

                @Override
                public void onError(int i) {

                }
            });
        } else {
            if (mMessagingChannelListQuery != null) {
                mMessagingChannelListQuery.cancel();
                mMessagingChannelListQuery = null;
            }
            chattingRoomAdapter.clear();
            recyclerView.removeOnScrollListener(onScrollListener);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (PropertyManager.getInstance().getSignInState() != PropertyManager.SIGN_OUT_STATE) {
            if (mMessagingChannelListQuery != null) {
                mMessagingChannelListQuery.cancel();
                mMessagingChannelListQuery = null;
            }
            if (BuildConfig.DEBUG)
                Log.d(TAG, "CANCEL DISCONNECT");
            SendBird.cancelAll();
            SendBird.disconnect();
        }
    }

    @Override
    public void onChattingRoomAdapterClick(View view, ChattingRoomItem item, int position) {
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("messageChannelUrl", item.getMessagingChannel().getUrl());
        intent.putExtra("APP_ID", appId);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("USER_NAME", userName);
        for (MessagingChannel.Member member : item.getMessagingChannel().getMembers())
            if (!member.getId().equals(SendBird.getUserId())) {
                intent.putExtra("TARGET_USER_NAME", member.getName());
                break;
            }
        intent.putExtra("bicycleName", item.getBicycleName());
        intent.putExtra("JOIN", true);
        startActivity(intent);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if ((linearLayoutManager.findLastVisibleItemPosition() == chattingRoomAdapter.getItemCount() - 1)
                    && recyclerView.getChildCount() > 0) {
                if (mMessagingChannelListQuery != null
                        && !mMessagingChannelListQuery.isLoading()
                        && mMessagingChannelListQuery.hasNext()) {
                    mMessagingChannelListQuery.next(new MessagingChannelListQuery.MessagingChannelListQueryResult() {
                        @Override
                        public void onResult(List<MessagingChannel> messagingChannels) {
                            String reservationState = null;
                            int i = 0;

                            for (MessagingChannel messagingChannel : messagingChannels) {
                                switch (++i % 3) {
                                    case 0:
                                        reservationState = "RR";
                                        break;
                                    case 1:
                                        reservationState = "RS";
                                        break;
                                    case 2:
                                        reservationState = "RC";
                                        break;
                                }

                                chattingRoomAdapter.add(
                                        new ChattingRoomItem(
                                                messagingChannel,
                                                reservationState,
                                                "Bicycle Name" + i
                                        )
                                );
                            }
                        }

                        @Override
                        public void onError(int i) {
                        }
                    });
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
}