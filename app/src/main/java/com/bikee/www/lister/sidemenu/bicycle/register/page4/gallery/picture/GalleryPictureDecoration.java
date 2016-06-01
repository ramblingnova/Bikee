package com.bikee.www.lister.sidemenu.bicycle.register.page4.gallery.picture;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User on 2016-02-24.
 */
public class GalleryPictureDecoration extends RecyclerView.ItemDecoration {
    private int lastRow;
    private int spanCount;
    private int space;

    public GalleryPictureDecoration(int lastRow, int spanCount, int space) {
        this.lastRow = lastRow;
        this.spanCount = spanCount;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        outRect.top = 0;
        outRect.left = (space / 2);
        outRect.right = (space / 2);

        if (lastRow == ((position + 1) / spanCount) + (((position + 1) % spanCount) == 0 ? 0 : 1))
            outRect.bottom = 0;
        else
            outRect.bottom = space;
    }
}
