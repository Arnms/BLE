package com.example.arnm.wearlovely;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016-08-11.
 */
public class AddBeaconsFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AddBeaconsAdapter mAdapter;

    public AddBeaconsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_device, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.fad_viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.fad_tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("직접 등록"));
        mTabLayout.addTab(mTabLayout.newTab().setText("주변 비콘 등록"));

        mAdapter = new AddBeaconsAdapter(getFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
            }
        });

        return view;
    }
}
