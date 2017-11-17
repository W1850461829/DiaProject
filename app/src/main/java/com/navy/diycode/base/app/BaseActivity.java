package com.navy.diycode.base.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.navy.diycode.R;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ViewHolder mViewHolder;
    private Toast mToast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mViewHolder = new ViewHolder(getLayoutInflater(), null, getLayoutId());
        setContentView(mViewHolder.getRootView());
    }

    protected abstract int getLayoutId();

    /**
     * 初始化数据，调用位置在 initViews 之前
     */
    protected void initDatas() {
    }

    /**
     * 初始化 View， 调用位置在 initDatas 之后
     */
    protected abstract void initViews(ViewHolder holder, View root);

    // 初始化 ActiobBar
    private void initActionBar(ViewHolder holder) {
        Toolbar toolbar = holder.get(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

    }
    // 默认点击左上角是结束当前 Activity


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public ViewHolder getmViewHolder() {
        return mViewHolder;
    }

    /**
     * 发出一个短Toast
     *
     * @param text 内容
     */
    public void toastShort(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 发出一个长toast提醒
     *
     * @param text 内容
     */
    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }

    private void toast(final String text, final int duration) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text, duration);
                    } else {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    }
                    mToast.show();
                }
            });
        }
    }

    protected void openActivity(Class<?> cls) {
        openActivity(this, cls);

    }

    private void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * 打开 Activity 的同时传递一个数据
     */
    protected <V extends Serializable> void openActivity(Context context, Class<?> cls, String key, V value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }
}
