package com.example.tacademy.bikee;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
        iv = (ImageView)findViewById(R.id.view_chatting_room_item_image_view);
        tv1 = (TextView)findViewById(R.id.view_chatting_room_item_bicycle_name);
        tv2 = (TextView)findViewById(R.id.view_chatting_room_item_payment_type_height);
        tv3 = (TextView)findViewById(R.id.view_chatting_room_item_distance);
    }

    public void setText(ChattingRoomItem item) {
        tv1.setText("" + item.tv1);
        tv2.setText("" + item.tv2);
        tv3.setText("" + item.tv3);
    }
}
