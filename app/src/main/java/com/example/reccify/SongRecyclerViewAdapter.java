package com.example.reccify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<SavedSongsModel> savedSongsModels;

    public SongRecyclerViewAdapter(Context context, ArrayList<SavedSongsModel> model) {
        this.context = context;
        this.savedSongsModels = model;
    }

    @NonNull
    @Override
    public SongRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new SongRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.name.setText(savedSongsModels.get(position).getSaveSong());
        holder.imageView.setImageResource(savedSongsModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return savedSongsModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.savedSongs);

        }
    }

}
