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
    TextView tv1, tv2, tv3;

    private void init() {
        inflate(getContext(), R.layout.view_chatting_room_item, this);
        iv = (ImageView)findViewById(R.id.view_chatting_room_item_bicycle_picture_image_view);
        tv1 = (TextView)findViewById(R.id.view_chatting_room_item_bicycle_name_text_view);
        tv2 = (TextView)findViewById(R.id.view_chatting_room_item_payment_type_height_text_view);
        tv3 = (TextView)findViewById(R.id.view_chatting_room_item_distance_text_view);
    }

    public void setText(ChattingRoomItem item) {
        tv1.setText("" + item.tv1);
        tv2.setText("" + item.tv2);
        tv3.setText("" + item.tv3);
    }
}
