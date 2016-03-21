package com.example.tacademy.bikee.common.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.chatting.room.ConversationActivity;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomsFragment extends Fragment implements OnChattingRoomAdapterClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.fragment_chatting_rooms_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.fragment_chatting_rooms_recycler_view)
    RecyclerView recyclerView;
    private ChattingRoomAdapter chattingRoomAdapter;
    private static final String TAG = "CHATTING_ROOMS_FRAGMENT";

    public static ChattingRoomsFragment newInstance() {
        return new ChattingRoomsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting_rooms , container, false);

        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);

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
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onChattingRoomAdapterClick(View view, ChattingRoomItem item, int position) {
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("userName", item.getUserName());
        intent.putExtra("bicycleName", item.getBicycleName());
        startActivity(intent);
    }

    private void init() {
        for (int i = 0; i < 30; i++) {
            String reservationState = null;
            switch (i % 3) {
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
            Date lastConversationTime = new Date();
            lastConversationTime.setTime(System.currentTimeMillis());
            chattingRoomAdapter.add(
                    new ChattingRoomItem(
                            "",
                            reservationState,
                            "User Name" + i,
                            lastConversationTime,
                            "Bicycle Name" + i,
                            "Last Conversation" + i,
                            i
                    )
            );
        }
    }
}
