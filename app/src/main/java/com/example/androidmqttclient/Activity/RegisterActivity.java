package com.example.androidmqttclient.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.R;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.databinding.ActivityRegisterBinding;

//账号注册界面
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ActivityRegisterBinding binding;

    @Override
    public void initData(@Nullable Bundle bundle) {
        StatusBar.setStatusBarColor(this, R.color.colorAccent);
    }

    @Override
    public View bindLayout() {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness() {
        binding.buttonEsc.setOnClickListener(this);
        binding.buttonRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonEsc.getId()) {
            finish();
        } else if (v.getId() == binding.buttonRegister.getId()) {
            //将信息上传到Server
            String id = binding.editId.getText().toString();
            String name = binding.editName.getText().toString();
            String password = binding.editPsd.getText().toString();
            String major = binding.editMajor.getText().toString();
            String studentNumber = binding.editStudentNumber.getText().toString();
            String department = binding.editDepartment.getText().toString();

            SharedPreferences.Editor edit = getSharedPreferences("account_number",
                                                                 Context.MODE_PRIVATE).edit();
            edit.putString("id", id);
            edit.putString("name", name);
            edit.putString("major", major);
            edit.putString("password", password);
            edit.putString("department", department);
            edit.putString("studentNumber", studentNumber);
            edit.apply();
            Toast.makeText(getApplicationContext(), "账号注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
