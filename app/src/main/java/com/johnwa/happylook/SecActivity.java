package com.johnwa.happylook;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.johnwa.happylook.adapter.SecAdapter;

import java.util.ArrayList;

/**
 * Created by qzh.
 * Date: 2021/4/19
 */
public class SecActivity extends AppCompatActivity {

    private RecyclerView picRecyclerView;
    private SecAdapter adapter;
    private SpinKitView picLoadView;
    private ImageView btn_Back;
    private TextView pic_Name;
    private ArrayList<String> mList;
    private String picName;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_layout);
        picName = getIntent().getStringExtra("picName");
        mList = getIntent().getStringArrayListExtra("picUrl");
        initView();
    }

    private void initView() {
        picRecyclerView = findViewById(R.id.pic_recycler_view);
        btn_Back = findViewById(R.id.btn_back);
        pic_Name = findViewById(R.id.pic_name);
        picLoadView = findViewById(R.id.pic_load_view);
        btn_Back.setOnClickListener(view -> {
            Tools.sendMessage(1008612);
            finish();
        });
        String title = picName +" ("+mList.size()+"p) ";
        pic_Name.setText(title);
        adapter = new SecAdapter(SecActivity.this , mList);
        manager = new LinearLayoutManager(this);
        picRecyclerView.setLayoutManager(manager);
        picRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Tools.sendMessage(1008612);
        super.onBackPressed();
    }
}
