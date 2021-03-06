package com.example.administrator.draglist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.administrator.draglist.MainActivity.MENU_ADD_REQUEST;
import static com.example.administrator.draglist.MainActivity.MENU_EDIT_REQUEST;
import static com.example.administrator.draglist.MainActivity.checkedItem;
import static com.example.administrator.draglist.MainActivity.isDeleteMode;
import static com.example.administrator.draglist.MainActivity.isMainSelect;
import static com.example.administrator.draglist.MainActivity.isMoveMode;
import static com.example.administrator.draglist.MainActivity.selectedPosition;

/**
 * Created by Administrator on 2017-07-15-015.
 */

public class TestAdapter extends RecyclerView.Adapter<TestViewHolder> implements StudentTouchCallback.OnItemMoveListener,TestViewHolder.OnListItemClickListener {

    List<MenuData> items = new ArrayList<>();

    public void addItem(MenuData data){
        items.add(data);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
    }


    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_data,parent,false);

        TestViewHolder holder = new TestViewHolder(v);
        holder.setOnListItemClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(final TestViewHolder holder, int position) {
        MenuData item = items.get(position);
        holder.itemView.setTag(item.getMenuKey());
        holder.menuDataName.setText(item.getMenuDataName());
        holder.menuDataPrice.setText(item.getMenuDataPrice());
        holder.menuDataExplain.setText(item.getMenuDataExplain());
        holder.option.setText(item.getOption());
        if (item.getIsMain()) {
            holder.isMainText.setVisibility(View.VISIBLE);
        }
        else{
            holder.isMainText.setVisibility(View.INVISIBLE);
        }

        //이미지 url
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("market").child(item.getUid()).child("menu").child(item.getMenuKey() + ".jpg");

        try {
            Glide
                    .with(context)
                    .using(new FirebaseImageLoader())
                    .load(ref)
//                        .listener(new RequestListener<StorageReference, GlideDrawable>() {
//                            @Override
//                            public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                view.menuDataImage.setScaleType(ImageView.ScaleType.F);
//                                return false;
//                            }
//                        })
                    .override(300, 300)
                    .signature(new StringSignature(item.getATime()))
                    .placeholder(R.drawable.jamsil)
                    .thumbnail(0.1f)
                    .into(holder.menuDataImage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isDeleteMode) {
            if (selectedPosition[position]) {
                holder.itemView.setBackgroundColor(Color.rgb(103, 153, 255));
            } else {
                holder.itemView.setBackgroundColor(Color.rgb(255, 255, 255));
            }
        } else if (isMainSelect) {
            if (position == checkedItem) {
                holder.itemView.setBackgroundColor(Color.rgb(103, 153, 255));
            }
            else{
                holder.itemView.setBackgroundColor(Color.rgb(255, 255, 255));
            }
        }
        else{
            holder.itemView.setBackgroundColor(Color.rgb(255, 255, 255));
        }


        holder.menuDataImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isMoveMode) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        startDragListener.onStartDrag(holder);
                    }
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

    @Override
    public void onListItemClick(View view,int position) {

        if (isDeleteMode) {
            if (selectedPosition[position]) {
                selectedPosition[position] = false;
                view.setBackgroundColor(Color.rgb(255, 255, 255));
            } else {
                selectedPosition[position] = true;
                view.setBackgroundColor(Color.rgb(103, 153, 255));
            }
            int i = 0;
            for (boolean a : selectedPosition) {
                i++;
                Log.d("불리언", i + Boolean.toString(a));
            }
        } else if (isMainSelect) {
            checkedItem = position;
            notifyDataSetChanged();
            view.setBackgroundColor(Color.rgb(103, 153, 255));
//                    if (oldPosition < parent.getChildCount())
//                        parent.getChildAt(oldPosition).setBackgroundColor(Color.rgb(255, 255, 255));
//                    oldPosition = position;

        } else {
            Intent intent = new Intent(context, MenuAddActivity.class);
            intent.putExtra("menuId", MainActivity.key[position]);
            intent.putExtra("isEdit", true);
            ((MainActivity) context).startActivityForResult(intent,MENU_ADD_REQUEST);
        }
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
