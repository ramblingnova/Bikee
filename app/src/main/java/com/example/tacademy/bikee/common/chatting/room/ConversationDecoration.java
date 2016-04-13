package com.example.tacademy.bikee.common.chatting.room;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;

/**
 * Created by User on 2016-03-14.
 */
public class ConversationDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ConversationDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        ConversationItem conversationItem = ((ConversationAdapter)parent.getAdapter()).getItemList().get(position);

        if (conversationItem.isSingleMessage())
            outRect.bottom = space;
        else {
            if (conversationItem.getMultiMessageType() == ConversationAdapter.FINAL)
                outRect.bottom = space;
            else
                outRect.bottom = 0;
        }

        if (position == 0)
            outRect.top = MyApplication.getmContext()
                    .getResources()
                    .getDimensionPixelOffset(R.dimen.view_holder_conversation_first_item_top_space);
    }
}
