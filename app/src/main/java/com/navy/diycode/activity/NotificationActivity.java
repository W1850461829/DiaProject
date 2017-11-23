package com.navy.diycode.activity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.navy.diycode.R;
import com.navy.diycode.base.app.BaseActivity;
import com.navy.diycode.base.app.ViewHolder;
import com.navy.diycode.fragment.NotificationsFragment;

public class NotificationActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        setTitle("通知");
        NotificationsFragment fragment = NotificationsFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment, fragment);
        transaction.commit();
    }
}
