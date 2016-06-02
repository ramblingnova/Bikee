package com.bigtion.bikee.lister.sidemenu.bicycle;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.MyApplication;

/**
 * Created by User on 2016-05-27.
 */
public class BicycleDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        if (position == 0)
            outRect.top = MyApplication.getmContext()
                    .getResources()
                    .getDimensionPixelOffset(R.dimen.view_holder_bicycle_first_item_top_space);
    }
}
