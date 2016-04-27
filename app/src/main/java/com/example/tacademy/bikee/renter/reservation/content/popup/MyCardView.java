package com.example.tacademy.bikee.renter.reservation.content.popup;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016-04-27.
 */
public class MyCardView extends FrameLayout {
    @Bind(R.id.view_my_card_nickname_text_view)
    TextView nicknameTextView;

    public MyCardView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_my_card, this);
        ButterKnife.bind(this);
    }

    public void setView(CardItem item) {
        nicknameTextView.setText("" + item.getNickname());
    }
}
