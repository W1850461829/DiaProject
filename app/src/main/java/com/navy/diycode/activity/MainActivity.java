package com.navy.diycode.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


import com.navy.diycode.R;
import com.navy.diycode.base.app.BaseActivity;
import com.navy.diycode.base.app.ViewHolder;
import com.navy.diycode.fragment.NewsListFragment;
import com.navy.diycode.fragment.SitesListFragment;
import com.navy.diycode.fragment.TopicListFragment;
import com.navy.diycode.test.TextFragment;
import com.navy.diycode.utils.Config;
import com.navy.diycode.utils.DataCache;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private boolean isToolbarFirstClick = true;
    private int mCurrentPosition = 0;
    private TopicListFragment mFragment1;
    private NewsListFragment mFragment2;
    private SitesListFragment mFragment3;
    private Config mConfig;
    private DataCache mCache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mCache = new DataCache(this);
        mConfig = Config.getSingleInstance();
        initMenu(holder);
       // initViewPager(holder);
    }

    private void initViewPager(ViewHolder holder) {
        ViewPager mViewPager = holder.get(R.id.view_pager);
        TabLayout mTabLayout = holder.get(R.id.tab_layout);
        mViewPager.setOffscreenPageLimit(3);

        mFragment1 = TopicListFragment.newInstance();
        mFragment2 = NewsListFragment.newInstance();
        mFragment3 = SitesListFragment.newInstance();
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] types = {"Topics", "News", "Sites"};

            @Override
            public Fragment getItem(int position) {
                if (position == 0)
                    return mFragment1;
                if (position == 1)
                    return mFragment2;
                if (position == 2)
                    return mFragment3;
                return TextFragment.newInstance(types[position]);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return types[position];
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // mCurrentPosition = mConfig.getMainViewPagerPosition();
        mViewPager.setCurrentItem(mCurrentPosition);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initMenu(ViewHolder holder) {
        Toolbar toolbar = holder.get(R.id.toolbar);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.logo_actionbar);
        DrawerLayout drawer = holder.get(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final GestureDetector detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }
        });
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return false;
            }
        });
        toolbar.setOnClickListener(this);
        holder.setOnClickListener(this, R.id.fab);
        loadMenuData();
    }

    private void loadMenuData() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                if (isToolbarFirstClick) {
                    toastLong("双击标题栏快速返回顶部");
                    isToolbarFirstClick = true;

                }
                break;
            case R.id.fab:
                quickToTop();
                break;
        }


    }

    private void quickToTop() {
        switch (mCurrentPosition) {
          /*  case 0:
                mFragment1.quickToTop();
                break;
            case 1:
                mFragment2.quickToTop();
                break;
            case 2:
                mFragment3.quickToTop();
                break;*/

        }

    }


}
