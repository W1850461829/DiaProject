package com.navy.diycode.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.navy.diycode.R;
import com.navy.diycode.base.app.BaseActivity;
import com.navy.diycode.base.app.ViewHolder;
import com.navy.diycode.fragment.UserCollectionTopicFragment;
import com.navy.diycode.fragment.UserCreateTopicFragment;
import com.navy.diycode.utils.DataCache;

import java.io.IOException;

public class MyTopicActivity extends BaseActivity {
    private DataCache mDataCache;
    private InfoType current_type = InfoType.MY_TOPIC;

    enum InfoType {
        MY_TOPIC, MY_COLLECT

    }

    public static void newInstance(Context context, InfoType type) {
        Intent intent = new Intent(context, MyTopicActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initDatas() {
        mDiycode = Diycode.getSingleInstance();
        mDataCache = new DataCache(this);
        Intent intent = getIntent();
        InfoType type = (InfoType) intent.getSerializableExtra("type");
        if (type != null) {
            current_type = type;

        } else {
            current_type = InfoType.MY_TOPIC;
        }

    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        if (!mDiycode.isLogin()) {
            toastLong("用户登录");
            return;
        }

        // 获取用户名
        if (mDataCache.getMe() == null) {
            try {
                UserDetail me = mDiycode.getMeNow();
                mDataCache.saveMe(me);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String username = mDataCache.getMe().getLogin();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (current_type == InfoType.MY_COLLECT) {
            setTitle("我的收藏");
            UserCollectionTopicFragment fragment1 = UserCollectionTopicFragment.newInstance(username);
            transaction.add(R.id.fragment, fragment1);
        } else if (current_type == InfoType.MY_TOPIC) {
            setTitle("我的话题");
            UserCreateTopicFragment fragment2 = UserCreateTopicFragment.newInstance(username);
            transaction.add(R.id.fragment, fragment2);
        }
        transaction.commit();
    }
}
