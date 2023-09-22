package com.example.assessment2;



import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    List<String> mediaList;

    public PhotoAdapter(List<String> mList) {
        this.mediaList = mList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=  layoutInflater.inflate(R.layout.layout_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int screenHeight = holder.imageView.getResources().getDisplayMetrics().heightPixels;
        int itemHeight = screenHeight / 3;
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        layoutParams.height = itemHeight;
        holder.imageView.setLayoutParams(layoutParams);

        Glide.with(holder.imageView.getContext())
                .load(Uri.parse(mediaList.get(position)))
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    String uri = mediaList.get(adapterPosition);
                    Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra("imageUri", uri);
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.ViewItem);

        }
    }
}