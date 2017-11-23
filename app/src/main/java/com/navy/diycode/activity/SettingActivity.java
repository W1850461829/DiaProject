package com.navy.diycode.activity;

import android.view.View;

import com.navy.diycode.R;
import com.navy.diycode.base.app.BaseActivity;
import com.navy.diycode.base.app.ViewHolder;
import com.navy.diycode.utils.AppUtil;
import com.navy.diycode.utils.Config;
import com.navy.diycode.utils.DataCleanManager;
import com.navy.diycode.utils.FileUtil;
import com.navy.diycode.utils.IntentUtil;

import java.io.File;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Config mConfig;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        setTitle("设置");
        mConfig = Config.getSingleInstance();
        showCacheSize(holder);

        String versionName = AppUtil.getVersionName(this);
        holder.setText(R.id.app_version, versionName);

        if (mDiycode.isLogin()) {
            holder.get(R.id.user).setVisibility(View.VISIBLE);
        } else {
            holder.get(R.id.user).setVisibility(View.GONE);
        }
        holder.setOnClickListener(this, R.id.clear_cache, R.id.logout, R.id.about, R.id.contribute);
    }

    private void showCacheSize(ViewHolder holder) {
        File cacheDir = new File(FileUtil.getExternalCacheDir(this));
        try {
            String cacheSize = DataCleanManager.getCacheSize(cacheDir);
            if (!cacheSize.isEmpty()) {
                holder.setText(R.id.cache_size, cacheSize);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                if (!mDiycode.isLogin()) {
                    return;
                }
                mDiycode.logout();
                toastShort("退出成功");
                break;
            case R.id.clear_cache:
                DataCleanManager.deleteFolderFile(FileUtil.getExternalCacheDir(this), false);
                showCacheSize(getViewHolder());
                toastShort("清除缓存成功");
                break;
            case R.id.about:
                openActivity(AboutActivity.class);
                break;
            case R.id.contribute:
                IntentUtil.openAlipay(this);
                break;
        }
    }
}
