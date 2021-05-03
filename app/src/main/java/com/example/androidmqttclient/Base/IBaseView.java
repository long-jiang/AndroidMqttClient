package com.example.androidmqttclient.Base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public interface IBaseView {

    void initData(@Nullable Bundle bundle/*获取通过意图传递过来的数据*/);

    View bindLayout();

    void setContentView();

    void initView(@Nullable Bundle savedInstanceState);

    void doBusiness();
}
