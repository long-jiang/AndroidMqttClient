package com.example.androidmqttclient.Adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmqttclient.R;

import java.util.List;

public class ControlAdapter extends RecyclerView.Adapter<ControlAdapter.ControlHolder> {
    private List<Pair<String, View>> mList;

    public ControlAdapter(List<Pair<String, View>> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ControlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.control_block, parent, false);
        return new ControlHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ControlHolder holder, int position) {
        holder.layout.setTag(mList.get(position).first);
        holder.layout.addView(mList.get(position).second);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ControlHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;

        public ControlHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.linear_control_block);
        }
    }
}
