package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.folder;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User on 2016-02-26.
 */
public class GalleryFolderDecoration extends RecyclerView.ItemDecoration {
    private int lastRow;
    private int space;

    public GalleryFolderDecoration(int lastRow, int space) {
        this.lastRow = lastRow;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        outRect.top = 0;

        if (lastRow == (position + 1))
            outRect.bottom = 0;
        else
            outRect.bottom = space;
    }
}
