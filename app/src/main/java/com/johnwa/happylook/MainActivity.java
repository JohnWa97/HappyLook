package com.johnwa.happylook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.johnwa.happylook.adapter.MainAdapter;
import com.johnwa.happylook.bean.Pictures;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {

    public static String TAG = "qzh";
    private String picName;
    private FloatingActionButton btnRefresh;
    private TextView tagName;
    private SpinKitView loadView;
    private RecyclerView recyclerView;
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Tools.initData(Tools.photoUrl,0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @SuppressLint("WrongViewCast")
    public void initView(){
        recyclerView = findViewById(R.id.main_recycler);
        tagName = findViewById(R.id.tag_name);
        loadView = findViewById(R.id.load_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter(this ,this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 10086:
                loadView.setVisibility(View.INVISIBLE);
                adapter.setList((List<Pictures>)msg.obj);
                recyclerView.setAdapter(adapter);
                break;
            case 1008611:
                loadView.setVisibility(View.INVISIBLE);
                ArrayList<String> mList = (ArrayList<String>)msg.obj;
                Intent intent = new Intent(MainActivity.this,SecActivity.class);
                intent.putExtra("picName",picName);
                intent.putStringArrayListExtra("picUrl", mList);
                startActivity(intent);
                break;
            case 1008612:
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case 1008613:
                Toast.makeText(this, "出现错误！！", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void clickListener(View v, String categoryName, String moreUrl) {
        loadView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        picName = categoryName;
        Tools.initData(moreUrl , 1);
    }
}