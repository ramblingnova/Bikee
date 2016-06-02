package com.bigtion.bikee.common.content.popup;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigtion.bikee.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by User on 2016-04-27.
 */
public class MyCardView extends FrameLayout {
    @Bind(R.id.view_my_card_checkbox)
    CheckBox checkBox;
    @Bind(R.id.view_my_card_nickname_text_view)
    TextView nicknameTextView;

    private OnMyCardViewCheckedChangeListener onMyCardViewCheckedChangeListener;

    public MyCardView(Context context) {
        super(context);

        inflate(getContext(), R.layout.view_my_card, this);

        ButterKnife.bind(this);
    }

    @OnCheckedChanged(R.id.view_my_card_checkbox)
    void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            onMyCardViewCheckedChangeListener.onMyCardViewCheckedChange(buttonView);
        }
    }

    public void setView(CardItem item) {
        nicknameTextView.setText("" + item.getNickname());
    }

    public void setOnMyCardViewCheckedChangeListener(OnMyCardViewCheckedChangeListener onMyCardViewCheckedChangeListener) {
        this.onMyCardViewCheckedChangeListener = onMyCardViewCheckedChangeListener;
    }
}
