package com.example.tacademy.bikee;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class SendTalkView extends FrameLayout {
    public SendTalkView(Context context) {
        super(context);
        init();
    }

    TextView date;
    TextView message;

    private void init() {
        inflate(getContext(), R.layout.view_send_talk_item, this);
        date = (TextView) findViewById(R.id.send_date);
        message = (TextView) findViewById(R.id.send_talk);
    }

    public void setText(SendTalkItem item) {
        date.setText("" + item.date);
        message.setText("" + item.message);
    }
}
