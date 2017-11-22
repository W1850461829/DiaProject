package com.navy.diycode.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;
import com.gcssloop.diycode_sdk.api.user.bean.User;
import com.gcssloop.recyclerview.adapter.base.RecyclerViewHolder;
import com.gcssloop.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.navy.diycode.R;
import com.navy.diycode.activity.UserActivity;
import com.navy.diycode.base.glide.GlideImageGetter;
import com.navy.diycode.utils.HtmlUtil;
import com.navy.diycode.utils.ImageUtils;
import com.navy.diycode.utils.TimeUtil;

/**
 * Created by Administrator on 2017/11/22.
 */

public class TopicReplyAdapter extends SingleTypeAdapter<TopicReply> {
    private Context mContext;

    public TopicReplyAdapter(@NonNull Context context) {
        super(context, R.layout.item_topic_reply);
        mContext = context;

    }

    @Override
    public void convert(int position, RecyclerViewHolder holder, TopicReply bean) {
        final User user = bean.getUser();
        holder.setText(R.id.username, user.getLogin());
        holder.setText(R.id.time, TimeUtil.computePastTime(bean.getUpdated_at()));

        ImageView avatar = holder.get(R.id.avatar);
        ImageUtils.loadImage(mContext, user.getAvatar_url(), avatar);
        TextView content = holder.get(R.id.content);

        content.setText(Html.fromHtml(HtmlUtil.removeP(bean.getBody_html()), new GlideImageGetter(mContext, content), null));

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra(UserActivity.USER, user);
                mContext.startActivity(intent);
            }
        }, R.id.avatar, R.id.username);

    }
}
