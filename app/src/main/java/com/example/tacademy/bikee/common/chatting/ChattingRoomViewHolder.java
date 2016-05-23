package com.example.tacademy.bikee.common.chatting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.interfaces.OnViewHolderClickListener;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.sendbird.android.SendBird;
import com.sendbird.android.model.MessagingChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_holder_chatting_room_user_picture_image_view)
    ImageView userImage;
    @Bind(R.id.view_holder_chatting_room_reservation_status_image_view)
    ImageView reservationState;
    @Bind(R.id.view_holder_chatting_room_user_name_text_view)
    TextView userName;
    @Bind(R.id.view_holder_chatting_room_last_conversation_time_text_view)
    TextView lastConversationTime;
    @Bind(R.id.view_holder_chatting_room_bicycle_title_text_view)
    TextView bicycleName;
    @Bind(R.id.view_holder_chatting_room_last_conversation_text_view)
    TextView lastConversation;
    @Bind(R.id.view_holder_chatting_room_num_of_stacked_conversation_text_view)
    TextView numOfStackedConversation;

    private OnViewHolderClickListener onViewHolderClickListener;

    public ChattingRoomViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    public void setView(ChattingRoomItem item) {
        String opponentImageUrl = null;
        for (MessagingChannel.Member member : item.getMessagingChannel().getMembers())
            if (!member.getId().equals(SendBird.getUserId())) {
                opponentImageUrl = member.getImageUrl();
                break;
            }
        ImageUtil.setCircleImageFromURL(
                MyApplication.getmContext(),
                opponentImageUrl,
                R.drawable.noneimage,
                userImage
        );

        Date currentDate = new Date();
        switch (item.getReservationState()) {
            case "RR":
                reservationState.setVisibility(View.VISIBLE);
                if (currentDate.after(item.getRentStart()))
                    reservationState.setImageResource(R.drawable.chatting_icon_step4);
                else
                    reservationState.setImageResource(R.drawable.chatting_icon_step1);
                break;
            case "RS":
                reservationState.setVisibility(View.VISIBLE);
                if (currentDate.after(item.getRentStart()))
                    reservationState.setImageResource(R.drawable.chatting_icon_step4);
                else
                    reservationState.setImageResource(R.drawable.chatting_icon_step2);
                break;
            case "PS":
                reservationState.setVisibility(View.VISIBLE);
                if (currentDate.after(item.getRentStart())) {
                    if (currentDate.after(item.getRentEnd()))
                        reservationState.setImageResource(R.drawable.chatting_icon_step4);
                    else
                        reservationState.setImageResource(R.drawable.chatting_icon_step3);
                } else
                    reservationState.setImageResource(R.drawable.chatting_icon_step2);
                break;
            case "RC":case "PC":
                reservationState.setVisibility(View.VISIBLE);
                reservationState.setImageResource(R.drawable.chatting_icon_step4);
                break;
            default:
                reservationState.setVisibility(View.INVISIBLE);
                break;
        }

        for (MessagingChannel.Member member : item.getMessagingChannel().getMembers())
            if (!member.getId().equals(SendBird.getUserId())) {
                userName.setText(member.getName());
                break;
            }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm", java.util.Locale.getDefault());
        lastConversationTime.setText(simpleDateFormat.format(new Date(item.getMessagingChannel().getLastMessageTimestamp())));

        bicycleName.setText(item.getBicycleName());

        lastConversation.setText(item.getMessagingChannel().getLastMessage().getMessage());

        if (item.getMessagingChannel().getUnreadMessageCount() == 0)
            numOfStackedConversation.setVisibility(View.GONE);
        else if (item.getMessagingChannel().getUnreadMessageCount() > 0) {
            numOfStackedConversation.setVisibility(View.VISIBLE);
            numOfStackedConversation.setText("" + item.getMessagingChannel().getUnreadMessageCount());
        }
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }

    @OnClick(R.id.view_chatting_room_item_layout)
    void onClick(View view) {
        if (onViewHolderClickListener != null)
            onViewHolderClickListener.onViewHolderClick(view);
    }
}
