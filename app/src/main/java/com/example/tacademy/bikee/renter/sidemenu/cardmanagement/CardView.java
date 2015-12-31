package com.example.tacademy.bikee.renter.sidemenu.cardmanagement;

import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardView extends FrameLayout {
    @Bind(R.id.view_card_item_card_number_text_view)
    TextView card_number;
    @Bind(R.id.view_card_item_delete_button)
    Button delete_button;

    public CardView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_card_item, this);
        ButterKnife.bind(this);
    }

    public void setView(CardItem item) {
        card_number.setText(item.card_number);
    }
}
