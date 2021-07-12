package com.example.androidmqttclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding loginBinding;

    @Override
    public void initData(@Nullable Bundle bundle) {
        StatusBar.translucentStatusBar(this);
    }

    @Override
    public View bindLayout() {
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        return loginBinding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        loginBinding.buttonLogin.setOnClickListener(this);
        loginBinding.buttonRegister.setOnClickListener(this);
        loginBinding.buttonForgetThePassword.setOnClickListener(this);
        SharedPreferences preferences = getSharedPreferences("account_number",
                                                             Context.MODE_PRIVATE);
        String id = preferences.getString("id", "default");
        String password = preferences.getString("password", "default");
        if (!id.equals("default")) {
            loginBinding.editId.setText(id);
        }

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == loginBinding.buttonLogin.getId()) {
            SharedPreferences preferences = getSharedPreferences("account_number",
                                                                 Context.MODE_PRIVATE);
            String id = preferences.getString("id", "default");
            String name = preferences.getString("name", "default");
            String password = preferences.getString("password", "default");
            if (id.equals(loginBinding.editId.getText().toString())) {
                if (password.equals(loginBinding.editPassword.getText().toString())) {
                    SharedPreferences.Editor edit =
                            getSharedPreferences("login_state", Context.MODE_PRIVATE).edit();
                    edit.putString("name", name);
                    edit.putBoolean("login", true).apply();
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    intent.putExtra("name", name);
                    intent.putExtra("position", "login_successful");
                    setResult(RESULT_OK, intent);
                    Intent homePageIntent = new Intent(this, HomepageActivity.class);
                    startActivity(homePageIntent);
                    finish();
                } else {
                    Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == loginBinding.buttonRegister.getId()) {//账号注册
            //调用注册界面
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (v.getId() == loginBinding.buttonForgetThePassword.getId()) {//忘记密码
            Toast.makeText(this, "暂未开放该功能", Toast.LENGTH_SHORT).show();
        }
    }
}
