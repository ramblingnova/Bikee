package com.bikee.www.renter.sidemenu.creditcard.register.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bikee.www.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016-05-17.
 */
public class SelectDateSpinnerItemView extends FrameLayout {
    @Bind(R.id.view_holder_select_date_text_view)
    TextView textView;

    public SelectDateSpinnerItemView(Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_select_date, this, false);

        ButterKnife.bind(this, view);

        addView(view);
    }

    public void setView(String item) {
        textView.setText(item);
    }
}
