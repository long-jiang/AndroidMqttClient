package com.example.androidmqttclient.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidmqttclient.AppConfig.AppConfig;
import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.R;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.Util.Tools;
import com.example.androidmqttclient.databinding.ActivityAboutBinding;

public class AboutActivity extends BaseActivity implements View.OnClickListener{

    private ActivityAboutBinding binding;

    @Override
    public void initData(@Nullable Bundle bundle) {
        StatusBar.setStatusBarColor(this, R.color.colorAccent);
    }

    @Override
    public View bindLayout() {
        binding=ActivityAboutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding.includeTitleBar.textFinish.setText("");
        binding.includeTitleBar.textViewTitle.setText("关于");
        binding.includeTitleBar.textReturn.setOnClickListener(this);
        binding.textViewVersion.setText(Tools.getVersionName(getBaseContext()));
        binding.textViewContactDetails.setText(AppConfig.ContactDetails);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onClick(View v) {
        if(v== binding.includeTitleBar.textReturn){
            finish();
        }
    }
}
