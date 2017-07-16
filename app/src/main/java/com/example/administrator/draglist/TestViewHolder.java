package com.example.administrator.draglist;

import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-15-015.
 */

public class TestViewHolder extends RecyclerView.ViewHolder{

    public TextView nameView,scoreView;
    public Button moveButton;

    public TestViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView)itemView.findViewById(R.id.nameView);
        scoreView = (TextView)itemView.findViewById(R.id.scoreView);
        moveButton = (Button)itemView.findViewById(R.id.moveButton);
    }
}
