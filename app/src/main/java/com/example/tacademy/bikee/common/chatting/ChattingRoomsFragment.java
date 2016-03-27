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
import com.sendbird.android.MessagingChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.model.MessagingChannel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomsFragment extends Fragment implements OnChattingRoomAdapterClickListener {
    @Bind(R.id.fragment_chatting_rooms_recycler_view)
    RecyclerView recyclerView;
    private ChattingRoomAdapter chattingRoomAdapter;
    private static final String TAG = "CHATTING_ROOMS_FRAGMENT";
    private String appId;
    private String userId;
    private String userName;

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

        Bundle args = getArguments();
        appId = args.getString("APP_ID");
        userId = args.getString("USER_ID");
        userName = args.getString("USER_NAME");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        chattingRoomAdapter = new ChattingRoomAdapter();
        chattingRoomAdapter.setOnChattingRoomAdapterClickListener(this);
        init();
        recyclerView.setAdapter(chattingRoomAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.addItemDecoration(
                new ChattingRoomDecoration(
                        getResources().getDimensionPixelSize(R.dimen.view_holder_chatting_room_item_space)
                )
        );

        return view;
    }

    @Override
    public void onChattingRoomAdapterClick(View view, ChattingRoomItem item, int position) {
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("messageChannelUrl", item.getMessageChannelUrl());
        intent.putExtra("APP_ID", appId);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("USER_NAME", userName);
        intent.putExtra("userName", item.getUserName());
        intent.putExtra("bicycleName", item.getBicycleName());
        startActivity(intent);
    }

    private void init() {
        MessagingChannelListQuery mMessagingChannelListQuery = SendBird.queryMessagingChannelList();
        mMessagingChannelListQuery.setLimit(30);
        mMessagingChannelListQuery.next(new MessagingChannelListQuery.MessagingChannelListQueryResult() {
            @Override
            public void onResult(List<MessagingChannel> list) {
                String messageChannelUrl;
                String userImage = null;
                String reservationState = null;
                String userName = null;
                Date lastConversationTime;
                String bicycleName = null;
                String lastConversation;
                int numOfStackedConversation;
                int i = 0;

                for (MessagingChannel messagingChannel : list) {
                    for (MessagingChannel.Member member : messagingChannel.getMembers()) {
                        if (!member.getId().equals(SendBird.getUserId())) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "member.getId : " + member.getId()
                                                + ", member.getImageUrl : " + member.getImageUrl()
                                                + ", member.getName : " + member.getName()
                                );
                            userImage = member.getImageUrl();
                            userName = member.getName();
                        }
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm", java.util.Locale.getDefault());
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "messagingChannel.getId : " + messagingChannel.getId()
                                        + ", messagingChannel.getUrl : " + messagingChannel.getUrl()
                                        + ", messagingChannel.getLastMessage.getMessage : " + messagingChannel.getLastMessage().getMessage()
                                        + ", messagingChannel.getLastMessageTimestamp : " + messagingChannel.getLastMessageTimestamp()
                                        + ", messagingChannel.getUnreadMessageCount : " + messagingChannel.getUnreadMessageCount()
                                        + ", refinedLastMessageTimestamp : " + simpleDateFormat.format(new Date(messagingChannel.getLastMessageTimestamp()))
                        );
                    messageChannelUrl = messagingChannel.getUrl();
                    lastConversation = messagingChannel.getLastMessage().getMessage();
                    lastConversationTime = new Date(messagingChannel.getLastMessageTimestamp());
                    numOfStackedConversation = messagingChannel.getUnreadMessageCount();

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
                                    messageChannelUrl,
                                    userImage,
                                    reservationState,
                                    userName,
                                    lastConversationTime,
                                    "Bicycle Name" + i,
                                    lastConversation,
                                    numOfStackedConversation
                            )
                    );
                }

                SendBird.connect();
            }

            @Override
            public void onError(int i) {

            }
        });

//        for (int i = 0; i < 30; i++) {
//            String reservationState = null;
//            switch (i % 3) {
//                case 0:
//                    reservationState = "RR";
//                    break;
//                case 1:
//                    reservationState = "RS";
//                    break;
//                case 2:
//                    reservationState = "RC";
//                    break;
//            }
//            Date lastConversationTime = new Date();
//            lastConversationTime.setTime(System.currentTimeMillis());
//            chattingRoomAdapter.add(
//                    new ChattingRoomItem(
//                            "",
//                            reservationState,
//                            "User Name" + i,
//                            lastConversationTime,
//                            "Bicycle Name" + i,
//                            "Last Conversation" + i,
//                            i
//                    )
//            );
//        }
    }
}
