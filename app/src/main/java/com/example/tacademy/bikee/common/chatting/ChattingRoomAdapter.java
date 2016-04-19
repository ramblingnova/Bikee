package com.example.tacademy.bikee.common.chatting;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.GetChannelInfoReceiveObject;
import com.example.tacademy.bikee.etc.dao.GetChannelResInfoReceiveObject;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.SendBirdSendObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
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
    private OnChattingRoomAdapterClickListener onChattingRoomAdapterClickListener;

    private static final String TAG = "CHATTING_ROOM_ADAPTER";

    public ChattingRoomAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public ChattingRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_chatting_room_item, parent, false);

        ChattingRoomViewHolder chattingRoomViewHolder = new ChattingRoomViewHolder(view);

        return chattingRoomViewHolder;
    }

    @Override
    public void onBindViewHolder(ChattingRoomViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnChattingRoomClickListener(new OnChattingRoomClickListener() {
            @Override
            public void onChattingRoomClick(View view) {
                notifyDataSetChanged();
                if (onChattingRoomAdapterClickListener != null)
                    onChattingRoomAdapterClickListener.onChattingRoomAdapterClick(view, list.get(position), position);
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

    public void addAll(List<ChattingRoomItem> listForAddAll) {
        list.addAll(listForAddAll);
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
                newItem.setReservationState(item.getReservationState());
                newItem.setBicycleName(item.getBicycleName());
                newItem.setBicycleId(item.getBicycleId());

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
                                                            newItem.setReservationState("");
                                                            newItem.setBicycleName(receiveObject.getResult().get(0).getTitle());
                                                            newItem.setBicycleId(bicycleId);

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
                                            newItem.setReservationState(receiveObject.getResult().get(0).getReserve().getStatus());
                                            newItem.setBicycleName(receiveObject.getResult().get(0).getBike().getTitle());
                                            newItem.setBicycleId(bicycleId);

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

    public void setOnChattingRoomAdapterClickListener(OnChattingRoomAdapterClickListener onChattingRoomAdapterClickListener) {
        this.onChattingRoomAdapterClickListener = onChattingRoomAdapterClickListener;
    }
}
