package com.example.androidmqttclient.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private ActivityLoginBinding loginBinding;
    @Override
    public void initData(@Nullable Bundle bundle) {
         StatusBar.translucentStatusBar(this);
    }

    @Override
    public View bindLayout() {
        loginBinding=ActivityLoginBinding.inflate(getLayoutInflater());
        return loginBinding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        loginBinding.buttonLogin.setOnClickListener(this);

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==loginBinding.buttonLogin.getId()){
            finish();
        }
    }
}
