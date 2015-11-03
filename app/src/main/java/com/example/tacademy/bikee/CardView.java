package com.example.tacademy.bikee;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardView extends FrameLayout {
    public CardView(Context context) {
        super(context);
        init();
    }

    TextView cardNum;

    private void init() {
        inflate(getContext(), R.layout.view_card_item, this);
        cardNum = (TextView) findViewById(R.id.view_card_item_card_number_text_view);
    }

    public void setText(CardItem item) {
        cardNum.setText("" + item.tv1);
    }
}
