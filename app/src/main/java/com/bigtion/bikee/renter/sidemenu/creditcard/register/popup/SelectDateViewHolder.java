package com.bigtion.bikee.renter.sidemenu.creditcard.register.popup;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.bigtion.bikee.etc.interfaces.OnViewHolderClickListener;
import com.bigtion.bikee.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2016-05-12.
 */
public class SelectDateViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_holder_select_date_text_view)
    TextView itemTextView;

    private OnViewHolderClickListener onViewHolderClickListener;

    public SelectDateViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.view_holder_select_date_text_view)
    void onClick(View view) {
        onViewHolderClickListener.onViewHolderClick(view);
    }

    public void setView(String item) {
        if (item.equals("직접입력"))
            itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        else
            itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        itemTextView.setText(item);
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }
}
