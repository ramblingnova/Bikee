package com.bigtion.bikee.common.popup;

import android.view.View;

/**
 * Created by User on 2016-06-03.
 */
public interface OnAddressDialogFragmentClickListener {
    void onAddressDialogFragmentClick(
            View view,
            String address,
            double latitude,
            double longitude
    );
}
