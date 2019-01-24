package com.daniel.JoyRent.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daniel.JoyRent.House.widget.HousesFragment;
import com.daniel.JoyRent.House.widget.nav_secondFrament;
import com.daniel.JoyRent.Rental.RentalHouses;
import com.daniel.JoyRent.about.widget.AboutFragment;
import com.daniel.JoyRent.login.Login;
import com.daniel.JoyRent.login.fakeNoLoginfragment;
import com.daniel.JoyRent.main.presenter.MainPresenter;
import com.daniel.JoyRent.main.presenter.MainPresenterImpl;
import com.daniel.JoyRent.main.view.MainView;
import com.lauren.simplenews.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.roughike.bottombar.TabSelectionInterceptor;



public class MainActivity extends AppCompatActivity implements MainView {
    /**
     * 导航栏的 activity
     */
    private BottomBarTab nearby;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private MainPresenter mMainPresenter;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);//mMainPresenter.switchNavigation(  2

        mMainPresenter = new MainPresenterImpl(this);//切换的初始化mMainPresenter.switchNavigation(menuItem.getItemId());**************************************************************************

        switch2News();




        //底部
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
             /*   if (tabId == R.id.tab_nearby) {
                    // 选择指定 id 的标签
                    nearby = bottomBar.getTabWithId(R.id.tab_nearby);
                    nearby.setBadgeCount(5);
                }*/
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {

                if (tabId == R.id.tab_HomePage) {
                    // 已经选择了这个标签，又点击一次。即重选。
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HousesFragment()).commit();//源生API里的切换其他页面，NewsFragment里 我现在换成house的
                }
            }
        });

        bottomBar.setTabSelectionInterceptor(new TabSelectionInterceptor() {
            @Override
            public boolean shouldInterceptTabSelection(@IdRes int oldTabId, @IdRes int newTabId) {
                // 点击无效
                if (newTabId == R.id.tab_mine ) {
                    // 返回 true 。代表：这里我处理了，你不用管了。
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new fakeNoLoginfragment()).commit();//源生API里的切换其他页面，
                    return true;
                }
                if (newTabId == R.id.tab_nearby) {
                    // 已经选择了这个标签，又点击一次。即重选。
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,RentalHouses.class);
                    startActivity(intent);
                    return true;
                    // nearby.removeBadge();
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

   /*     menu.add(Menu.NONE,  Menu.FIRST+1, 0, "设置").setIcon(R.drawable.biz_plugin_weather_baoyu);
        menu.add(Menu.NONE,  Menu.FIRST+2, 0, "设置").setIcon(R.drawable.biz_plugin_weather_baoyu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.put) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,RentalHouses.class);
            startActivity(intent);

            return true;
        }if (id == R.id.action_settings) {


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mMainPresenter.switchNavigation(menuItem.getItemId());//真*切换
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void switch2News() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HousesFragment()).commit();//源生API里的切换其他页面，NewsFragment里 我现在换成house的
        mToolbar.setTitle("我的首页");//主页的标题  // 标题名字  存在 navigation_menu.xml
    }

    @Override
    public void switch2Houses() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new nav_secondFrament()).commit();//源生API里的切换其他页面，
        mToolbar.setTitle("房源");//主页的标题
    }


    @Override
    public void switch2About() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AboutFragment()).commit();
        mToolbar.setTitle(R.string.navigation_about);
    }

    public void UserInfo1(View view) {
      //  startActivity(new Intent(this,com.daniel.JoyRent.login.UserInfo.class));
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Login.class);
        startActivity(intent);
    }
    public void rentalHouse(View view) {
        //  startActivity(new Intent(this,com.daniel.JoyRent.login.UserInfo.class));
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,RentalHouses.class);
        startActivity(intent);


     /*   Intent intent = new Intent(getActivity(), RentalHouses.class);
        startActivity(intent);*/
    }


}