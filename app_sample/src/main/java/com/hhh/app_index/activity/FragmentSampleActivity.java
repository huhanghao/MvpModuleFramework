package com.hhh.app_index.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.hhh.app_index.R;
import com.hhh.app_index.R2;
import com.hhh.app_index.adapter.SampleMainActAdapter;
import com.hhh.app_index.fragment.FirstFragment;
import com.hhh.app_index.fragment.ListFragment;
import com.hhh.app_index.fragment.SelfDefineFragment;
import com.hhh.lib_base.XActivity;
import com.hhh.lib_base.base_mvp.IBasePresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentSampleActivity extends XActivity {


    @BindView(R2.id.vp_main)
    ViewPager mPager;

    @BindView(R2.id.bnv_navigation)
    BottomNavigationView bnvNavigation;



    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public void addView(Bundle savedInstanceState) {
        addMainView(R.layout.app_sample_activity_fragment_sample);

        setMidTitle("fragment_sample");

        initView();
    }

    private void initView() {
        mFragmentList.add(new FirstFragment());
        mFragmentList.add(new ListFragment());
        mFragmentList.add(new SelfDefineFragment());

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        disableShiftMode(bnvNavigation);
        bnvNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.navigation_home) {
                    mPager.setCurrentItem(0);
                    return true;
                } else if (i == R.id.navigation_friends) {
                    mPager.setCurrentItem(1);
                    return true;
                } else if (i == R.id.navigation_mine) {
                    mPager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });

        SampleMainActAdapter mainAdapter = new SampleMainActAdapter(mFragmentList, getSupportFragmentManager());


        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mainAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bnvNavigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        bnvNavigation.setSelectedItemId(R.id.navigation_friends);
                        break;
                    case 2:
                        bnvNavigation.setSelectedItemId(R.id.navigation_mine);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public IBasePresenter setPresenter() {
        return null;
    }



    /**
     * 去除BottomNavigationView底部效果
     *
     * @param view
     */
    @SuppressLint("RestrictedApi")
    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

}
