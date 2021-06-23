package com.johnwa.happylook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.johnwa.happylook.R;

import java.util.List;

/**
 * Created by qzh.
 * Date: 2021/4/19
 */
public class SecAdapter extends RecyclerView.Adapter<SecAdapter.ViewHolder> {

    private Context context;
    private List<String> picList;

    public SecAdapter(Context context , List<String> picList){
        this.context = context;
        this.picList = picList;
    }

    @NonNull
    @Override
    public SecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.pic_item_layout,null,false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String src = picList.get(position);
        Glide.with(context).load("http:"+src).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return picList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public ViewHolder( View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pic_item);
        }
    }

}
