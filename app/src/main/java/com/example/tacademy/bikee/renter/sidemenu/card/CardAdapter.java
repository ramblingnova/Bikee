package com.example.tacademy.bikee.renter.sidemenu.card;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.interfaces.OnAdapterClickListener;
import com.example.tacademy.bikee.etc.dao.CardReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private List<CardItem> list;
    private OnAdapterClickListener onAdapterClickListener;

    private static final String TAG = "CARD_ADAPTER";

    public CardAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_card, parent, false);

        CardViewHolder holder = new CardViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnCardViewClickListener(new OnCardViewClickListener() {
            @Override
            public void onCardImageClick(View view) {
                if (onAdapterClickListener != null)
                    onAdapterClickListener.onAdapterClick(view, list.get(position));
            }

            @Override
            public void onDeleteCardClick(View view) {
                if (onAdapterClickListener != null)
                    onAdapterClickListener.onAdapterClick(view, list.get(position));

                NetworkManager.getInstance().deleteCard(
                        list.get(position).get_id(),
                        null,
                        new Callback<CardReceiveObject>() {
                            @Override
                            public void onResponse(Call<CardReceiveObject> call, Response<CardReceiveObject> response) {
                                CardReceiveObject cardReceiveObject = response.body();
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "deleteCard onResponse");

                                if (cardReceiveObject.getCode() == 200) {
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<CardReceiveObject> call, Throwable t) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "deleteCard onFailure", t);
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnAdapterClickListener(OnAdapterClickListener onAdapterClickListener) {
        this.onAdapterClickListener = onAdapterClickListener;
    }

    public void add(CardItem item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void addFirst(CardItem item) {
        list.add(0, item);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
