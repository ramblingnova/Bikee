package com.example.tacademy.bikee.common.chatting;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class ReceiveTalkView extends FrameLayout {
    public ReceiveTalkView(Context context) {
        super(context);
        init();
    }

    TextView date;
    TextView message;

    private void init() {
        inflate(getContext(), R.layout.view_receive_talk_item, this);
        date = (TextView) findViewById(R.id.receive_date);
        message = (TextView) findViewById(R.id.receive_talk);
    }

    public void setText(ReceiveTalkView item) {
        date.setText("" + item.date);
        message.setText("" + item.message);
    }
}
