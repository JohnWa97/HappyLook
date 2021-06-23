package com.johnwa.happylook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.johnwa.happylook.R;
import com.johnwa.happylook.bean.Pictures;

import java.util.List;

/**
 * Created by qzh.
 * Date: 2021/4/17
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>  {

    public List<Pictures> list;
    private final Context context;
    private final ItemClickListener mListener;

    public interface ItemClickListener{
        void clickListener(View v ,String categoryName , String moreUrl);
    }

    public MainAdapter (Context context , ItemClickListener listener ){
        this.context = context;
        mListener = listener;
    }

    public List<Pictures> getList() {
        return list;
    }

    public void setList(List<Pictures> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pictures pictures = list.get(position);
        holder.imgName.setText(pictures.getPicName());
        Glide.with(context).load("http:"+pictures.getPreViewImg()).into(holder.preImg);
        String mURL = "https:"+pictures.getPicUrl();
        holder.itemView.setOnClickListener(view -> mListener.clickListener(view , pictures.getPicName(),mURL));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView preImg;
        public TextView imgName;

        public ViewHolder(View itemView) {
            super(itemView);
            preImg = itemView.findViewById(R.id.pre_view_img);
            imgName = itemView.findViewById(R.id.img_name);
        }
    }
}
