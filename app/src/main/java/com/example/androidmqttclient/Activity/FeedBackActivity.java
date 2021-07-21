package com.example.androidmqttclient.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.databinding.ActivityFeedbackBinding;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener{
    private ActivityFeedbackBinding binding;

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }

    @Override
    public View bindLayout() {
        binding=ActivityFeedbackBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void initView(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onClick(View view) {

    }
}
