package com.example.administrator.draglist;

import android.content.ClipData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.administrator.draglist.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements TestAdapter.OnStartDragListener{
    public static final int MENU_ADD_REQUEST = 555;
    public static final int MENU_EDIT_REQUEST = 444;
    ActivityMainBinding binding;
    TestAdapter adapter;
    LinearLayoutManager layoutManager;
    ItemTouchHelper itemTouchHelper;
    DatabaseReference ref;
    Iterator<DataSnapshot> it;
    ValueEventListener listener;
    String key[];
    boolean selectedPosition[];
    boolean isDeleteMode;
    boolean isMainSelect;
    boolean refresh;
    int i;
    int menuCount;
    int checkedItem = -1;
    String uid;
    AlertDialog deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        uid ="slwVsecqtTO3RDjzPxBWrFekbEd2";

        binding.addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuAddActivity.class);
                intent.putExtra("isEdit", false);
                intent.putExtra("menuCount", menuCount + 1);
                startActivityForResult(intent, MENU_ADD_REQUEST);
            }
        });
        adapter = new TestAdapter(this,this);
        layoutManager = new LinearLayoutManager(this);
        binding.menuListView.setLayoutManager(layoutManager);

        StudentTouchCallback callback = new StudentTouchCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.menuListView);
        ref = FirebaseDatabase.getInstance().getReference("market");

        binding.menuListView.setAdapter(adapter);
        adapter.add(new Student("ㅋㅋ","1점"));
        adapter.add(new Student("ㅋㅋ","1점"));
        adapter.add(new Student("ㅋㅋ","1점"));
        adapter.add(new Student("ㅋㅋ","1점"));
        adapter.add(new Student("ㅋㅋ","1점"));
    }

    @Override
    public void onStartDrag(TestViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
