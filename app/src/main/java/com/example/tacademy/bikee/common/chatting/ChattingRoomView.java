package com.example.tacademy.bikee.common.chatting;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

/**
 * Created by User on 2015-10-31.
 */
public class ChattingRoomView extends FrameLayout {
    public ChattingRoomView(Context context) {
        super(context);
        init();
    }

    ImageView iv;
    private TextView bicycle_name;
    private TextView payment;
    private TextView type;
    private TextView height;
    private TextView distance;

    private void init() {
        inflate(getContext(), R.layout.view_chatting_room_item, this);
        iv = (ImageView)findViewById(R.id.view_chatting_room_item_bicycle_picture_image_view);
        bicycle_name = (TextView)findViewById(R.id.view_chatting_room_item_bicycle_name_text_view);
        payment = (TextView)findViewById(R.id.view_chatting_room_item_payment_text_view);
        type = (TextView)findViewById(R.id.view_chatting_room_item_type_text_view);
        height = (TextView)findViewById(R.id.view_chatting_room_item_height_text_view);
        distance = (TextView)findViewById(R.id.view_chatting_room_item_distance_text_view);
    }

    public void setText(ChattingRoomItem item) {
        bicycle_name.setText("" + item.getBicycle_name());
        payment.setText("" + item.getPayment());
        type.setText("" + item.getType());
        height.setText("" + item.getHeight());
        distance.setText("" + item.getDistance());
    }
}
