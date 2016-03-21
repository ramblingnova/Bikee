package com.example.tacademy.bikee.common.chatting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomViewHolder extends RecyclerView.ViewHolder {
    private OnChattingRoomClickListener onChattingRoomClickListener;
    @Bind(R.id.view_chatting_room_item_user_image_image_view)
    ImageView userImage;
    @Bind(R.id.view_chatting_room_item_reservation_state_image_view)
    ImageView reservationState;
    @Bind(R.id.view_chatting_room_item_user_name_text_view)
    TextView userName;
    @Bind(R.id.view_chatting_room_item_last_conversation_time_text_view)
    TextView lastConversationTime;
    @Bind(R.id.view_chatting_room_item_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.view_chatting_room_item_last_conversation_text_view)
    TextView lastConversation;
    @Bind(R.id.view_chatting_room_item_num_of_stacked_conversation_text_view)
    TextView numOfStackedConversation;

    public ChattingRoomViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    public void setView(ChattingRoomItem item) {
        ImageUtil.setCircleImageFromURL(MyApplication.getmContext(), item.getUserImage(), R.drawable.noneimage, 0, userImage);

        switch (item.getReservationState()) {
            case "RR":
                reservationState.setImageResource(R.drawable.chatting_icon_step1);
                break;
            case "RS":
                reservationState.setImageResource(R.drawable.chatting_icon_step2);
                break;
            case "RC":
                reservationState.setImageResource(R.drawable.chatting_icon_step3);
                break;
        }

        userName.setText(item.getUserName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm", java.util.Locale.getDefault());
        lastConversationTime.setText(simpleDateFormat.format(item.getLastConversationTime()));

        bicycleName.setText(item.getBicycleName());

        lastConversation.setText(item.getLastConversation());

        if (item.getNumOfStackedConversation() == 0)
            numOfStackedConversation.setVisibility(View.GONE);
        else if (item.getNumOfStackedConversation() > 0) {
            numOfStackedConversation.setVisibility(View.VISIBLE);
            numOfStackedConversation.setText("" + item.getNumOfStackedConversation());
        }
    }

    public void setOnChattingRoomClickListener(OnChattingRoomClickListener onChattingRoomClickListener) {
        this.onChattingRoomClickListener = onChattingRoomClickListener;
    }

    @OnClick(R.id.view_chatting_room_item_layout)
    void onClick(View view) {
        if (onChattingRoomClickListener != null)
            onChattingRoomClickListener.onChattingRoomClick(view);
    }
}
