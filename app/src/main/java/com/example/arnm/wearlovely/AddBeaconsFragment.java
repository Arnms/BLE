package com.example.arnm.wearlovely;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class AddBeaconsFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    public AddBeaconsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_device, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initViewPager(view);
    }

    private void initViewPager(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.fad_viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.fad_tablayout);

        List<Fragment> mListFragment = new ArrayList<>();
        mListFragment.add(new ABFirstFragment());
        mListFragment.add(new ABSecondFragment());

        AddBeaconsAdapter mAdapter = new AddBeaconsAdapter(getActivity().getSupportFragmentManager(), mListFragment);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.addTab(mTabLayout.newTab().setText("직접 등록"));
        mTabLayout.addTab(mTabLayout.newTab().setText("주변 비콘 등록"));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        mViewPager.setCurrentItem(tab.getPosition());
                        break;
                    case 1:
                        mViewPager.setCurrentItem(tab.getPosition());
                        break;
                    default: break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}
