package com.navy.diycode.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gcssloop.diycode_sdk.api.Diycode;
import com.navy.diycode.base.app.ViewHolder;
import com.navy.diycode.utils.Config;
import com.navy.diycode.utils.DataCache;

/**
 * Created by Administrator on 2017/11/20.
 */

public abstract class BaseFragment extends Fragment {

    private ViewHolder mViewHolder;
    protected Config mConfig;
    protected Diycode mDiycode;
    protected DataCache mDataCache;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(mViewHolder, mViewHolder.getRootView());
    }

    protected abstract void initViews(ViewHolder holder, View root);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewHolder = new ViewHolder(getLayoutInflater(), null, getLayoutId());
        return mViewHolder.getRootView();
    }

    protected abstract int getLayoutId();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = Config.getSingleInstance();
        mDiycode = Diycode.getSingleInstance();
        mDataCache = new DataCache(getContext());
    }

    public ViewHolder getViewHolder() {
        return mViewHolder;
    }

    protected void toast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
