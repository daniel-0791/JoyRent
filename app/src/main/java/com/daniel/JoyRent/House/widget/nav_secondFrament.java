package com.daniel.JoyRent.House.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.daniel.JoyRent.R;

import java.util.ArrayList;
import java.util.List;


public class nav_secondFrament extends Fragment {

    public static final int Houses_TYPE_LOCATTION =3;
    public static final int Houses_TYPE_SEX = 4;
    public static final int Houses_TYPE_BUDGET = 5;


    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_houses, null);
        mTablayout = (TabLayout) view.findViewById(R.id.tab_houseslayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewhousespager);
        mViewPager.setOffscreenPageLimit(3);
        setupViewPager(mViewPager);
        mTablayout.addTab(mTablayout.newTab().setText("全部"));
        mTablayout.addTab(mTablayout.newTab().setText("室友合租"));
        mTablayout.addTab(mTablayout.newTab().setText("本地")); //首页的顶部导航


        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(HousesListFragment.newInstance(Houses_TYPE_LOCATTION), "全部");//Houses_TYPE_LOCATTION 的值是0
        adapter.addFragment(HousesListFragment.newInstance(Houses_TYPE_SEX), "仅限女性");//newInstance 每次刷新响应
        adapter.addFragment(HousesListFragment.newInstance(Houses_TYPE_BUDGET), "附近(南昌县）");


        mViewPager.setAdapter(adapter);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
