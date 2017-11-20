package com.navy.diycode.fragment;

import android.os.Bundle;
import android.view.View;

import com.navy.diycode.base.app.ViewHolder;
import com.navy.diycode.fragment.base.BaseFragment;

/**
 * Created by Administrator on 2017/11/20.
 */

public class TopicListFragment extends BaseFragment {
    public static TopicListFragment newInstance() {
        Bundle args = new Bundle();
        TopicListFragment fragment = new TopicListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initViews(ViewHolder holder, View root) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
