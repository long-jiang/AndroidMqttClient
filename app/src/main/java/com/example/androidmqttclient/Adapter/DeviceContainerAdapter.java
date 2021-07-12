package com.example.androidmqttclient.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmqttclient.Bean.DeviceBean;
import com.example.androidmqttclient.Callback.DeviceBlockClick;
import com.example.androidmqttclient.R;

import java.util.List;

public class DeviceContainerAdapter extends RecyclerView.Adapter<DeviceContainerAdapter.ViewHolder> {
    private List<DeviceBean> mList;
    private DeviceBlockClick deviceBlockClick;

    public DeviceContainerAdapter(List<DeviceBean> list, DeviceBlockClick deviceBlockClick) {
        this.mList = list;
        this.deviceBlockClick=deviceBlockClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //   holder.linearLayout_device_block.setPadding(Tools.dp2px(clearance),Tools.dp2px(10),Tools.dp2px(clearance),Tools.dp2px(10));

        holder.textViewDeviceName.setText(mList.get(position).getDeviceName());
        //  holder.imageView.setImageResource(mList.get(position).getDeviceImage());
        holder.textViewDescribe.setText(mList.get(position).getDeviceDescribe());
        holder.linearLayout_device_block.setTag(mList.get(position).getDeviceId());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    //添加设备块
    public void addDeviceBlockView() {

    }

    //删除设备块
    public void subDeviceBlockView() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textViewDescribe;
        private final TextView textViewDeviceName;
        private final LinearLayout linearLayout_device_block;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ImageView imageView = itemView.findViewById(R.id.imageView);
            textViewDescribe = itemView.findViewById(R.id.textView_describe);
            textViewDeviceName = itemView.findViewById(R.id.textView_device_name);
            linearLayout_device_block = itemView.findViewById(R.id.linearLayout_device_block);
        }

        @Override
        public void onClick(View v) {
            deviceBlockClick.deviceBlockClick(v);
        }
    }
}
