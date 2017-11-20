package com.navy.diycode.base.app;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Administrator on 2017/11/16.
 */

public class ViewHolder {


    private final View mRootView;
    private SparseArray<View> mviews;


    public ViewHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
         this.mviews = new SparseArray<View>();
        mRootView = inflater.inflate(layoutId, parent);

    }
    /*public ViewHolder(Context context, ViewGroup parent,int layoutId) {
        this.mviews = new SparseArray<View>();
        mRootView = LayoutInflater.from(context).inflate(layoutId, parent,false);

    }*/

    /**
     * 通过view的id来获取子view
     *
     * @param resid view的id
     * @param <T>   泛型
     * @return 子view
     */
    public <T extends View> T get(int resid) {
        View view = mviews.get(resid);
        if (view == null) {
            view = mRootView.findViewById(resid);
            mviews.put(resid, view);
        }
        return (T) view;
    }

    /**
     * 获取布局View
     *
     * @return 布局View
     */
    public View getRootView() {
        return mRootView;
    }

    /**
     * 设置文本
     *
     * @param res_id view 的 id
     * @param text   文本内容
     * @return 是否成功
     */
    public boolean setText(CharSequence text, @NonNull int res_id) {
        try {
            TextView textView = get(res_id);
            textView.setText(text);
            return true;
        } catch (Exception e) {
            return false;

        }
    }

    public boolean setText(@NonNull int res_id, CharSequence text) {
        return setText(text, res_id);
    }

    public void loadImage(Context context, String url, int res_id) {
        ImageView imageView = get(res_id);
        String url2 = url;
        if (url.contains("divcode")) {
            url2 = url.replace("large_avatar", "avatar");
            Glide.with(context).load(url2).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        }
    }

    /**
     * 设置监听器
     *
     * @param l   监听器
     * @param ids view 的 id
     */
    public void setOnClickListener(View.OnClickListener l, int... ids) {
        if (ids == null) {
            return;

        }
        for (int id : ids) {
            get(id).setOnClickListener(l);

        }

    }

}
