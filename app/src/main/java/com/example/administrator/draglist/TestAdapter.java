package com.example.administrator.draglist;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017-07-15-015.
 */

public class TestAdapter extends RecyclerView.Adapter<TestViewHolder> implements StudentTouchCallback.OnItemMoveListener {

    List<Student> items = new ArrayList<>();

    public void add(Student data){
        items.add(data);
        notifyDataSetChanged();
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_view,parent,false);
        return new TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TestViewHolder holder, int position) {
        Student item = items.get(position);
        holder.nameView.setText(item.name);
        holder.scoreView.setText(item.score);

        holder.moveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                    startDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public boolean onItemMove(int fromPostion, int toPostion) {
        Collections.swap(items,fromPostion,toPostion);
        notifyItemMoved(fromPostion,toPostion);
        return false;
    }

    public interface OnStartDragListener{
        void onStartDrag(TestViewHolder viewHolder);
    }
    private final Context context;
    private final OnStartDragListener startDragListener;
    public TestAdapter(Context context,OnStartDragListener startDragListener){
        this.context = context;
        this.startDragListener = startDragListener;
    }
}
