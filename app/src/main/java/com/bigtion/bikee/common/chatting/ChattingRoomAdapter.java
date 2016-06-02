package com.bigtion.bikee.common.chatting;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtion.bikee.common.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.common.interfaces.OnViewHolderClickListener;
import com.bigtion.bikee.etc.dao.GetChannelInfoReceiveObject;
import com.bigtion.bikee.etc.dao.GetChannelResInfoReceiveObject;
import com.bigtion.bikee.etc.dao.SendBirdSendObject;
import com.bigtion.bikee.etc.manager.NetworkManager;
import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.dao.ReceiveObject;
import com.sendbird.android.SendBird;
import com.sendbird.android.model.MessagingChannel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomAdapter extends RecyclerView.Adapter<ChattingRoomViewHolder> {
    private List<ChattingRoomItem> list;
    private OnAdapterClickListener onAdapterClickListener;

    private static final String TAG = "CHATTING_ROOM_ADAPTER";

    public ChattingRoomAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public ChattingRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_chatting_room, parent, false);

        ChattingRoomViewHolder chattingRoomViewHolder = new ChattingRoomViewHolder(view);

        return chattingRoomViewHolder;
    }

    @Override
    public void onBindViewHolder(ChattingRoomViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnViewHolderClickListener(new OnViewHolderClickListener() {
            @Override
            public void onViewHolderClick(View view) {
                if (onAdapterClickListener != null)
                    onAdapterClickListener.onAdapterClick(view, list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(ChattingRoomItem item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void replace(final MessagingChannel messagingChannel) {
        final ChattingRoomItem newItem = new ChattingRoomItem();
        for (ChattingRoomItem item : list)
            if (item.getMessagingChannel().getId() == messagingChannel.getId()) {
                newItem.setMessagingChannel(messagingChannel);
                newItem.setRentStart(item.getRentStart());
                newItem.setRentEnd(item.getRentEnd());
                newItem.setReservationState(item.getReservationState());
                newItem.setBicycleName(item.getBicycleName());
                newItem.setBicycleId(item.getBicycleId());
                newItem.setAmILister(item.isAmILister());

                list.remove(item);
                list.add(0, newItem);
                notifyDataSetChanged();
                return;
            }

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

                                                            newItem.setMessagingChannel(messagingChannel);
                                                            newItem.setRentStart(null);
                                                            newItem.setRentEnd(null);
                                                            newItem.setReservationState("");
                                                            newItem.setBicycleName(receiveObject.getResult().get(0).getTitle());
                                                            newItem.setBicycleId(bicycleId);
                                                            newItem.setAmILister(amILister);

                                                            list.add(0, newItem);
                                                            notifyDataSetChanged();
                                                            return;
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                                            if (BuildConfig.DEBUG)
                                                                Log.d(TAG, "onFailure Error : " + t.toString());
                                                        }
                                                    });
                                        } else {
                                            newItem.setMessagingChannel(messagingChannel);
                                            newItem.setRentStart(receiveObject.getResult().get(0).getReserve().getRentStart());
                                            newItem.setRentEnd(receiveObject.getResult().get(0).getReserve().getRentEnd());
                                            newItem.setReservationState(receiveObject.getResult().get(0).getReserve().getStatus());
                                            newItem.setBicycleName(receiveObject.getResult().get(0).getBike().getTitle());
                                            newItem.setBicycleId(bicycleId);
                                            newItem.setAmILister(amILister);

                                            list.add(0, newItem);
                                            notifyDataSetChanged();
                                            return;
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

    public void setOnAdapterClickListener(OnAdapterClickListener onAdapterClickListener) {
        this.onAdapterClickListener = onAdapterClickListener;
    }
}
