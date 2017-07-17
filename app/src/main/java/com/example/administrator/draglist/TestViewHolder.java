package com.example.administrator.draglist;

import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-15-015.
 */

public class TestViewHolder extends RecyclerView.ViewHolder{
    TextView menuDataName;
    TextView menuDataPrice;
    TextView menuDataExplain;
    ImageView menuDataImage;
    TextView isMainText;
    TextView option;

    private OnListItemClickListener listItemClickListener;

    public interface OnListItemClickListener{
        public void onListItemClick(View v, int position);
    }

    public void setOnListItemClickListener(OnListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }

    public TestViewHolder(View itemView) {
        super(itemView);

        menuDataName=(TextView)itemView.findViewById(R.id.menuDataName);
        menuDataPrice=(TextView)itemView.findViewById(R.id.menuDataPrice);
        menuDataExplain=(TextView)itemView.findViewById(R.id.menuDataExplain);
        menuDataImage=(ImageView)itemView.findViewById(R.id.menuDataImage);
        isMainText = (TextView)itemView.findViewById(R.id.isMainText);
        option = (TextView)itemView.findViewById(R.id.optionText);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemClickListener.onListItemClick(view ,getAdapterPosition());
            }
        });
    }
}
