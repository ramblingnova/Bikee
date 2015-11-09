package com.example.tacademy.bikee.renter.sidemenu.cardmanagement;

import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardView extends FrameLayout {

    public CardView(Context context) {
        super(context);
        init();
    }

    TextView card_number;
    Button delete_button;

    private void init() {
        inflate(getContext(), R.layout.view_card_item, this);
        card_number = (TextView) findViewById(R.id.view_card_item_card_number_text_view);
        delete_button = (Button)findViewById(R.id.view_card_item_delete_button);
    }

    public void setCardView(CardItem item) {
        card_number.setText(item.card_number);
    }

//    public void setOnBtnClickListener(OnClickListener clickListener){
//        if(btn != null)
//            btn.setOnClickListener(clickListener);
//    }
}
