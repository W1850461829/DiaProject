/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-04-09 14:31:47
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.navy.diycode.fragment.Provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.gcssloop.recyclerview.adapter.base.RecyclerViewHolder;
import com.gcssloop.recyclerview.adapter.multitype.BaseViewProvider;
import com.navy.diycode.R;
import com.navy.diycode.fragment.bean.SiteItem;
import com.navy.diycode.utils.IntentUtil;

public class SiteProvider extends BaseViewProvider<SiteItem> {
    private Context mContext;

    public SiteProvider(@NonNull Context context) {
        super(context, R.layout.item_site);
        mContext = context;
    }

    /**
     * 在绑定数据时调用，需要用户自己处理
     *
     * @param holder ViewHolder
     * @param bean   数据
     */
    @Override
    public void onBindView(RecyclerViewHolder holder, final SiteItem bean) {
        if (bean.getName().isEmpty()) return;
        holder.setText(R.id.name, bean.getName());
        ImageView icon = holder.get(R.id.icon);
        Glide.with(mContext).load(bean.getAvatar_url()).into(icon);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.openUrl(mContext, bean.getUrl());
            }
        }, R.id.item);
    }
}
