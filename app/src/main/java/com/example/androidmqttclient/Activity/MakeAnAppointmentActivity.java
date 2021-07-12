package com.example.androidmqttclient.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.R;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.databinding.ActivityMakeAnAppointmentBinding;

import java.util.Date;

//场地预约界面
public class MakeAnAppointmentActivity extends BaseActivity implements View.OnClickListener {
    private ActivityMakeAnAppointmentBinding binding;

    //预约场地名称
    private String fieldName;

    private long endTime = 0L;
    private long startTime = 0L;

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        fieldName = bundle.getString("field_name");//获取场地名称
        StatusBar.setStatusBarColor(this, R.color.colorAccent);
    }

    @Override
    public View bindLayout() {
        binding = ActivityMakeAnAppointmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding.textViewFieldName.setText(fieldName);
        binding.textReturn.setOnClickListener(this);
        binding.textViewExit.setOnClickListener(this);
        binding.textViewEndTime.setOnClickListener(this);
        binding.textViewStartTime.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.textReturn) {
            //正常逻辑 判断是否输入数据 输入数据的情况下应该询问决定是否退出
            finish();//退出该界面
        } else if (v == binding.textViewExit) {
            //预约该体育场
            //保持预约数据
            if (startTime != 0L && endTime != 0L) {
                if (startTime >= endTime) {
                    Toast.makeText(getApplicationContext(), "预约开始时间大约结束时间", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                SharedPreferences.Editor edit =
                        getSharedPreferences((fieldName+"time"), Context.MODE_PRIVATE)
                                .edit();
                edit.putLong("start_time", startTime);
                edit.putLong("end_time", endTime).apply();
                Toast.makeText(getApplicationContext(), "场馆已经预约成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "数据格式不正确", Toast.LENGTH_SHORT).show();

            }
        } else if (v == binding.textViewStartTime) {
            datePickDialog(new OnSureLisener() {
                @Override
                public void onSure(Date date) {
                    startTime = date.getTime();
                    binding.textViewStartTime.setText(date.toLocaleString());
                }
            });
        } else if (v == binding.textViewEndTime) {
            datePickDialog(new OnSureLisener() {
                @Override
                public void onSure(Date date) {
                    endTime = date.getTime();
                    binding.textViewEndTime.setText(date.toLocaleString());
                }
            });
        }

    }

    //时间选择控件
    private void datePickDialog(OnSureLisener onSureLisener) {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(onSureLisener);
        dialog.show();
    }

}
