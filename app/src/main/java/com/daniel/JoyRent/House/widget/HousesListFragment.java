package com.daniel.JoyRent.House.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daniel.JoyRent.House.HousesAdapter;
import com.daniel.JoyRent.House.presenter.HousesPresenter;
import com.daniel.JoyRent.House.presenter.HousesPresenterImpl;
import com.daniel.JoyRent.House.view.HousesView;
import com.daniel.JoyRent.R;
import com.daniel.JoyRent.beans.HousesBean;
import com.daniel.JoyRent.commons.Urls;
import com.daniel.JoyRent.utils.LogUtils;

import com.roughike.bottombar.BottomBar;

import java.util.ArrayList;
import java.util.List;


public class HousesListFragment extends Fragment implements HousesView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HousesListFragment";
    private BottomBar bottomBar;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private HousesAdapter mAdapter;
    private List<HousesBean> mData;
    private HousesPresenter mHousesPresenter;

    private int mType = HousesFragment.Houses_TYPE_LOCATTION;  //房源首页的第一个地址
    private int pageIndex = 0;
        private  Button button;
    public static HousesListFragment newInstance(int type) {
        Bundle args = new Bundle();
        HousesListFragment fragment = new HousesListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHousesPresenter = new HousesPresenterImpl(this);
        mType = getArguments().getInt("type");






    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_houseslist, null);

        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new HousesAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        onRefresh();










        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int Housestate) {
            super.onScrollStateChanged(recyclerView, Housestate);
            if (Housestate == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.isShowFooter()) {
                //加载更多

                LogUtils.d(TAG, "loading more data");
                mHousesPresenter.loadHouses(mType, pageIndex + Urls.PAZE_SIZE);
            }
        }
    };

    private HousesAdapter.OnItemClickListener mOnItemClickListener = new HousesAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mData.size() <= 0) {
                return;
            }
            HousesBean Houses = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), HousesDetailActivity.class);
            intent.putExtra("Houses" , Houses);


            View transitionView = view.findViewById(R.id.ivHouses);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));//在transition_news_img.xml里

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

        }
    };

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }


    /*
       List<HousesBean>   mData
     */
    @Override
    public void addHouses(List<HousesBean> HousesList) {
        List<HousesBean> temp;
        mAdapter.isShowFooter(true);
        if(mData == null) {
            mData = new ArrayList<HousesBean>();
        }
        mData.clear(); //啊我的天，不要用null就行了。用clear清除掉，避免重复
        mData.addAll(HousesList);

        if(pageIndex == 0) {  //page索引

            mAdapter.setmDate(mData);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if(HousesList == null || HousesList.size() == 0) {
                mAdapter.isShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }


    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if(pageIndex == 0) {
            mAdapter.isShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);






    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
        }
        mHousesPresenter.loadHouses(mType, pageIndex);
    }


    //底部





}
