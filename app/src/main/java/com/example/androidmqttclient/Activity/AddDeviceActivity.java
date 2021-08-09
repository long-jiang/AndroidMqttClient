package com.example.androidmqttclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidmqttclient.R;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.databinding.ActivityAddDeviceBinding;
import com.orhanobut.logger.Logger;

/*
   添加设备界面
 */
public class AddDeviceActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAddDeviceBinding addDeviceBinding;

    @Override
    public void initData(@Nullable Bundle bundle) {
        StatusBar.setStatusBarColor(this, R.color.colorAccent);
    }

    @Override
    public View bindLayout() {
        addDeviceBinding = ActivityAddDeviceBinding.inflate(getLayoutInflater());
        return addDeviceBinding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        addDeviceBinding.textReturn.setOnClickListener(this);
        addDeviceBinding.textFinish.setOnClickListener(this);

        addDeviceBinding.checkBoxDistributionNetwork.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(addDeviceBinding.checkBoxSecretKey.isChecked()) {
                        addDeviceBinding.checkBoxSecretKey.toggle();
                    }
                }
            }
        });

         addDeviceBinding.checkBoxSecretKey.setOnCheckedChangeListener(
                 new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(addDeviceBinding.checkBoxDistributionNetwork.isChecked()) {
                        addDeviceBinding.checkBoxDistributionNetwork.toggle();
                    }
                }
            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addDeviceBinding.textReturn.getId()) {
            //需要询问是否保存后才退出 如果保存的就通知主页面创建一个设备块
            Logger.d("退出添加设备界面");
            finish();
        } else if (v.getId() == addDeviceBinding.textFinish.getId()) {
            String deviceName=addDeviceBinding.EditDeviceName.getText().toString();
            String deviceDescribe=addDeviceBinding.EditDescribe.getText().toString();
            String addMethod=addDeviceBinding.checkBoxSecretKey.isChecked()?"生成秘钥":"网络配网";
            String deviceId="123456789";//该id由请求服务器获得

            Logger.d("deviceId :",deviceId);

            //保存退出 跳转到控件编辑界面 并且通知主页创建设备块
            Intent data = new Intent();
            data.putExtra("deviceId", deviceId);
            data.putExtra("addMethod", addMethod);
            data.putExtra("deviceName", deviceName);
            data.putExtra("deviceDescribe", deviceDescribe);
            setResult(RESULT_OK, data);

            //调到控制界面 传设备id过去
            //Intent intent = new Intent(this, DeviceControlActivity.class);
           // intent.putExtra("device_id", "ASDFG125456");
            //startActivity(intent);
            finish();
        }
    }
}
