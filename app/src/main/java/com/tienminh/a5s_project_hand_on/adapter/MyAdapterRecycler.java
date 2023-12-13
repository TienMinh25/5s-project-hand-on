package com.tienminh.a5s_project_hand_on.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienminh.a5s_project_hand_on.R;
import com.tienminh.a5s_project_hand_on.classes.Room;
import com.tienminh.a5s_project_hand_on.database.DatabaseCallback;
import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterRecycler extends RecyclerView.Adapter<MyAdapterRecycler.MyViewHolder> {
    private ArrayList<Room> data;
    private Integer layout_id;
    public MyAdapterRecycler(List<Room> data, Integer layout_id) {
        this.data = (ArrayList<Room>) data;
        this.layout_id = layout_id;
    }

    public ArrayList<Room> getData() {
        return data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewTitle.setText(data.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
