package com.bigtion.bikee.common.popup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigtion.bikee.R;
import com.bigtion.bikee.common.interfaces.OnViewHolderClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2016-06-03.
 */
public class AddressViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_holder_address_text_view)
    TextView addressTextView;

    private OnViewHolderClickListener onViewHolderClickListener;

    public AddressViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.view_holder_address_text_view)
    void onClick(View view) {
        if (onViewHolderClickListener != null)
            onViewHolderClickListener.onViewHolderClick(view);
    }

    public void setView(AddressItem item) {
        addressTextView.setText(item.getAddress());
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }
}
