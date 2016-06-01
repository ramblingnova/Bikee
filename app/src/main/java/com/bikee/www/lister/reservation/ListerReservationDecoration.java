package com.bikee.www.lister.reservation;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bikee.www.etc.MyApplication;
import com.bikee.www.R;

/**
 * Created by User on 2016-05-27.
 */
public class ListerReservationDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        if (position == 0)
            outRect.top = MyApplication.getmContext()
                    .getResources()
                    .getDimensionPixelOffset(R.dimen.view_holder_lister_reservation_item_top_space);
    }
}
