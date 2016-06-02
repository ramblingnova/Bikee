package com.bigtion.bikee.common.chatting;

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

import com.bigtion.bikee.common.chatting.room.ConversationActivity;
import com.bigtion.bikee.common.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.etc.dao.GetChannelInfoReceiveObject;
import com.bigtion.bikee.etc.dao.GetChannelResInfoReceiveObject;
import com.bigtion.bikee.etc.dao.SendBirdSendObject;
import com.bigtion.bikee.etc.manager.NetworkManager;
import com.bigtion.bikee.etc.manager.PropertyManager;
import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.dao.ReceiveObject;
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
public class ChattingRoomsFragment extends Fragment implements OnAdapterClickListener {
    @Bind(R.id.fragment_chatting_rooms_recycler_view)
    RecyclerView recyclerView;

    private MessagingChannelListQuery mMessagingChannelListQuery;
    private LinearLayoutManager linearLayoutManager;
    private ChattingRoomAdapter chattingRoomAdapter;
    private String appId;
    private String userId;
    private String userName;
    private String gcmRegToken;
    private ChattingRoomItem[] waitingList;
    private int waitingCount = 0;

    private static final String TAG = "CHATTING_ROOMS_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting_rooms, container, false);

        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        chattingRoomAdapter = new ChattingRoomAdapter();
        chattingRoomAdapter.setOnAdapterClickListener(this);
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
        waitingCount = 0;

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
    public void onAdapterClick(View view, Object item) {
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("APP_ID", appId);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("USER_NAME", userName);
        for (MessagingChannel.Member member : ((ChattingRoomItem)item).getMessagingChannel().getMembers())
            if (!member.getId().equals(SendBird.getUserId())) {
                intent.putExtra("TARGET_USER_ID", member.getId());
                intent.putExtra("TARGET_USER_NAME", member.getName());
                break;
            }
        intent.putExtra("BICYCLE_ID", ((ChattingRoomItem)item).getBicycleId());
        intent.putExtra("BICYCLE_NAME", ((ChattingRoomItem)item).getBicycleName());
        intent.putExtra("CHANNEL_URL", ((ChattingRoomItem)item).getMessagingChannel().getUrl());
        intent.putExtra("AM_I_LISTER", ((ChattingRoomItem)item).isAmILister());
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

    public void addAll(int index, ChattingRoomItem chattingRoomItem) {
        waitingList[index] = chattingRoomItem;
        waitingCount++;

        if (waitingCount == waitingList.length)
            for (ChattingRoomItem item : waitingList)
                chattingRoomAdapter.add(item);
    }

    private void addItemsToAdapter(List<MessagingChannel> list) {
        waitingList = new ChattingRoomItem[list.size()];
        for (int i = 0; i < list.size(); i++) {
            final int index = i;
            final MessagingChannel messagingChannel = list.get(i);
            NetworkManager.getInstance().getChannelInfo(
                    messagingChannel.getUrl(),
                    null,
                    new Callback<GetChannelInfoReceiveObject>() {
                        @Override
                        public void onResponse(Call<GetChannelInfoReceiveObject> call, Response<GetChannelInfoReceiveObject> response) {
                            GetChannelInfoReceiveObject getChannelInfoReceiveObject = response.body();

                            SendBirdSendObject sendBirdSendObject = new SendBirdSendObject();
                            // TODO : out of bounds exception 발생
                            if (getChannelInfoReceiveObject != null)
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "getChannelInfoReceiveObject");
                            if (getChannelInfoReceiveObject.getResult() != null)
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "getChannelInfoReceiveObject.getResult()");
                            if (getChannelInfoReceiveObject.getResult().get(0) != null)
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "getChannelInfoReceiveObject.getResult().get(0)");
                            if (getChannelInfoReceiveObject.getResult().get(0).getRenter() != null)
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "getChannelInfoReceiveObject.getResult().get(0).getRenter()");
                            if (sendBirdSendObject != null)
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "sendBirdSendObject");
                            sendBirdSendObject.setRenter(getChannelInfoReceiveObject.getResult().get(0).getRenter());
                            sendBirdSendObject.setLister(getChannelInfoReceiveObject.getResult().get(0).getLister());
                            sendBirdSendObject.setBike(getChannelInfoReceiveObject.getResult().get(0).getBike());
                            final String bicycleId = getChannelInfoReceiveObject.getResult().get(0).getBike();
                            final boolean amILister = SendBird.getUserId().equals(getChannelInfoReceiveObject.getResult().get(0).getLister());

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
                                                                addAll(
                                                                        index,
                                                                        new ChattingRoomItem(
                                                                                messagingChannel,
                                                                                null,
                                                                                null,
                                                                                "",
                                                                                receiveObject.getResult().get(0).getTitle(),
                                                                                bicycleId,
                                                                                amILister
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
                                                addAll(
                                                        index,
                                                        new ChattingRoomItem(
                                                                messagingChannel,
                                                                receiveObject.getResult().get(0).getReserve().getRentStart(),
                                                                receiveObject.getResult().get(0).getReserve().getRentEnd(),
                                                                receiveObject.getResult().get(0).getReserve().getStatus(),
                                                                receiveObject.getResult().get(0).getBike().getTitle(),
                                                                bicycleId,
                                                                amILister
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