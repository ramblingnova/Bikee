package com.bikee.www.common.chatting;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User on 2016-03-15.
 */
public class ChattingRoomDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ChattingRoomDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
    }
}
