package com.navy.diycode.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent;
import com.gcssloop.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.navy.diycode.base.app.ViewHolder;
import com.navy.diycode.fragment.Provider.TopicProvider;
import com.navy.diycode.fragment.base.BaseFragment;
import com.navy.diycode.fragment.base.SimpleRefreshRecyclerFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/11/20.
 */

public class TopicListFragment extends SimpleRefreshRecyclerFragment<Topic, GetTopicsListEvent> {
    private boolean isFirstLaunch = true;

    public static TopicListFragment newInstance() {
        Bundle args = new Bundle();
        TopicListFragment fragment = new TopicListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initData(HeaderFooterAdapter adapter) {
        List<Object> topics = mDataCache.getTopicsListObj();
        if (null != topics && topics.size() > 0) {
            pageIndex = mConfig.getTopicListPageIndex();
            adapter.addDatas(topics);
            if (isFirstLaunch) {
                int lastPosition = mConfig.getTopicListLastPosition();
                mRecyclerView.getLayoutManager().scrollToPosition(lastPosition);
                isFirstAddFooter = false;
                isFirstLaunch = false;
            }

        } else {
            loadMore();
        }
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
        adapter.register(Topic.class, new TopicProvider(getContext()));
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        return mDiycode.getTopicsList(null, null, offset, limit);
    }

    @Override
    protected void onRefresh(GetTopicsListEvent event, HeaderFooterAdapter adapter) {
        super.onRefresh(event, adapter);
        mDataCache.saveTopicsListObj(adapter.getDatas());
    }

    @Override
    protected void onLoadMore(GetTopicsListEvent event, HeaderFooterAdapter adapter) {
        super.onLoadMore(event, adapter);
        mDataCache.saveTopicsListObj(adapter.getDatas());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mConfig.saveTopicListPageIndex(pageIndex);
        View view = mRecyclerView.getLayoutManager().getChildAt(0);
        int lastPosition = mRecyclerView.getLayoutManager().getPosition(view);
        mConfig.saveTopicListPageIndex(lastPosition);
    }
}
