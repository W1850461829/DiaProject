package com.navy.diycode.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.User;
import com.gcssloop.recyclerview.adapter.base.RecyclerViewHolder;
import com.gcssloop.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.gcssloop.view.utils.DensityUtils;
import com.github.florent37.expectanim.ExpectAnim;
import com.navy.diycode.R;
import com.navy.diycode.base.app.BaseActivity;
import com.navy.diycode.base.app.ViewHolder;
import com.navy.diycode.utils.ImageUtils;
import com.navy.diycode.utils.RecyclerViewUtil;
import com.navy.diycode.utils.TimeUtil;

import static com.github.florent37.expectanim.core.Expectations.alpha;
import static com.github.florent37.expectanim.core.Expectations.height;
import static com.github.florent37.expectanim.core.Expectations.leftOfParent;
import static com.github.florent37.expectanim.core.Expectations.sameCenterVerticalAs;
import static com.github.florent37.expectanim.core.Expectations.scale;
import static com.github.florent37.expectanim.core.Expectations.toRightOf;
import static com.github.florent37.expectanim.core.Expectations.topOfParent;

public class UserActivity extends BaseActivity implements View.OnClickListener {
    public static String USER = "user";
    private ExpectAnim expectAnimMove;
    private SingleTypeAdapter<Topic> mAdapter;

    public static void newInstance(Context context, User user) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER, user);
        context.startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        initUserInfo(holder);
        initRecycleView(holder);
        initScrollAnimation(holder);
    }

    private void initScrollAnimation(ViewHolder holder) {
        NestedScrollView scrollView = holder.get(R.id.scroll_view);
        ImageView avatar = holder.get(R.id.avatar);
        TextView username = holder.get(R.id.username);
        View backbground = holder.get(R.id.background);

        this.expectAnimMove = new ExpectAnim()
                .expect(avatar)
                .toBe(
                        topOfParent().withMarginDp(13),
                        leftOfParent().withMarginDp(13),
                        scale(0.5f, 0.5f)
                )
                .expect(username)
                .toBe(
                        toRightOf(avatar).withMarginDp(16),
                        sameCenterVerticalAs(avatar),
                        alpha(0.5f)
                )
                .expect(backbground)
                .toBe(
                        height(DensityUtils.dip2px(this, 60)).withGravity(Gravity.LEFT, Gravity.TOP)
                )
                .toAnimation();

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int
                    oldScrollX, int oldScrollY) {
                final float percent = (scrollY * 1f) / v.getMaxScrollAmount();
                expectAnimMove.setPercent(percent);
            }
        });
    }

    private void initRecycleView(ViewHolder holder) {
        mAdapter = new SingleTypeAdapter<Topic>(this, R.layout.item_topic) {
            @Override
            public void convert(int position, RecyclerViewHolder holder, final Topic bean) {
                User user = bean.getUser();
                holder.setText(R.id.username, user.getLogin());
                holder.setText(R.id.node_name, bean.getNode_name());
                holder.setText(R.id.time, TimeUtil.computePastTime(bean.getUpdated_at()));
                holder.setText(R.id.title, bean.getTitle());
                ImageView avatar = holder.get(R.id.avatar);
                ImageUtils.loadImage(mContext, user.getAvatar_url(), avatar);
                holder.get(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TopicContentActivity.newInstance(mContext, bean);
                    }
                });
                holder.get(R.id.node_name).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TopicActivity.newInstance(mContext, bean.getNode_id(), bean.getNode_name());
                    }
                });
            }
        };
        RecyclerView recyclerView = holder.get(R.id.recycler_view);
        RecyclerViewUtil.init(this, recyclerView, mAdapter);
    }

    private void initUserInfo(ViewHolder holder) {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra(USER);
        if (user != null) {
            setTitle(user.getLogin());
            holder.setText(user.getName(), R.id.username);
            holder.loadImage(this, user.getAvatar_url(), R.id.avatar);
            mDiycode.getUser(user.getLogin());

        }
    }

    @Override
    public void onClick(View view) {

    }
}
