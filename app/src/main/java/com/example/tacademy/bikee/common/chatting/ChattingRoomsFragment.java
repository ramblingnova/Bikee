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
import com.example.tacademy.bikee.etc.dao.GetChannelResInfoReceiveObject;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.SendBirdSendObject;
import com.example.tacademy.bikee.etc.dao.GetChannelInfoReceiveObject;
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

    private MessagingChannelListQuery mMessagingChannelListQuery;
    private LinearLayoutManager linearLayoutManager;
    private ChattingRoomAdapter chattingRoomAdapter;
    private String appId;
    private String userId;
    private String userName;
    private String gcmRegToken;

    private static final String TAG = "CHATTING_ROOMS_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting_rooms, container, false);

        ButterKnife.bind(this, view);

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

    @Override
    public void onResume() {
        super.onResume();

        if (PropertyManager.getInstance().getSignInState() != PropertyManager.SIGN_OUT_STATE) {
            appId = "2E377FE1-E1AD-4484-A66F-696AF1306F58";
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

            if (mMessagingChannelListQuery == null) {
                mMessagingChannelListQuery = SendBird.queryMessagingChannelList();
                mMessagingChannelListQuery.setLimit(30);
            }

            mMessagingChannelListQuery.next(new MessagingChannelListQuery.MessagingChannelListQueryResult() {
                @Override
                public void onResult(List<MessagingChannel> list) {
                    chattingRoomAdapter.clear();

                    addItemsToAdapter(list);

                    SendBird.join("");
                    SendBird.connect();
                }

                @Override
                public void onError(int i) {

                }
            });

            recyclerView.addOnScrollListener(onScrollListener);
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

            SendBird.cancelAll();
            SendBird.disconnect();
        }
    }

    @Override
    public void onChattingRoomAdapterClick(View view, ChattingRoomItem item, int position) {
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("APP_ID", appId);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("USER_NAME", userName);
        for (MessagingChannel.Member member : item.getMessagingChannel().getMembers())
            if (!member.getId().equals(SendBird.getUserId())) {
                intent.putExtra("TARGET_USER_ID", member.getId());
                intent.putExtra("TARGET_USER_NAME", member.getName());
                break;
            }
        intent.putExtra("BICYCLE_ID", item.getBicycleId());
        intent.putExtra("BICYCLE_NAME", item.getBicycleName());
        intent.putExtra("CHANNEL_URL", item.getMessagingChannel().getUrl());
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
                        public void onResult(List<MessagingChannel> list) {
                            addItemsToAdapter(list);
                        }

                        @Override
                        public void onError(int i) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onError");
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

    private void addItemsToAdapter(List<MessagingChannel> list) {
        for (final MessagingChannel messagingChannel : list) {
            NetworkManager.getInstance().getChannelInfo(
                    messagingChannel.getUrl(),
                    null,
                    new Callback<GetChannelInfoReceiveObject>() {
                        @Override
                        public void onResponse(Call<GetChannelInfoReceiveObject> call, Response<GetChannelInfoReceiveObject> response) {
                            GetChannelInfoReceiveObject getChannelInfoReceiveObject = response.body();

                            SendBirdSendObject sendBirdSendObject = new SendBirdSendObject();
                            sendBirdSendObject.setRenter(getChannelInfoReceiveObject.getResult().get(0).getRenter());
                            sendBirdSendObject.setLister(getChannelInfoReceiveObject.getResult().get(0).getLister());
                            sendBirdSendObject.setBike(getChannelInfoReceiveObject.getResult().get(0).getBike());
                            final String bicycleId = getChannelInfoReceiveObject.getResult().get(0).getBike();

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
                                                                ReceiveObject receiveObject = response.body();

                                                                chattingRoomAdapter.add(
                                                                        new ChattingRoomItem(
                                                                                messagingChannel,
                                                                                "",
                                                                                receiveObject.getResult().get(0).getTitle(),
                                                                                bicycleId
                                                                        )
                                                                );
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                                                if (BuildConfig.DEBUG)
                                                                    Log.d(TAG, "onFailure Error : " + t.toString());
                                                            }
                                                        });
                                            } else {
                                                chattingRoomAdapter.add(
                                                        new ChattingRoomItem(
                                                                messagingChannel,
                                                                receiveObject.getResult().get(0).getReserve().getStatus(),
                                                                receiveObject.getResult().get(0).getBike().getTitle(),
                                                                bicycleId
                                                        )
                                                );
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<GetChannelResInfoReceiveObject> call, Throwable t) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "onFailure Error : " + t.toString());
                                        }
                                    });
                        }

                        @Override
                        public void onFailure(Call<GetChannelInfoReceiveObject> call, Throwable t) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onFailure Error : " + t.toString());
                        }
                    });
        }
    }
}