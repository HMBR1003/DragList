package com.example.administrator.draglist;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2017-07-15-015.
 */

public class StudentTouchCallback extends ItemTouchHelper.Callback {

    public interface OnItemMoveListener{
        boolean onItemMove(int fromPostion, int toPostion);
    }
    private final OnItemMoveListener itemMoveListener;
    public StudentTouchCallback(OnItemMoveListener itemMoveListener){
        this.itemMoveListener=itemMoveListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        itemMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
