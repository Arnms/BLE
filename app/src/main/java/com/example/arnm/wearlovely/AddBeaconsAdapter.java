package com.example.arnm.wearlovely;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class AddBeaconsAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    private List<Fragment> mList;

    public AddBeaconsAdapter(FragmentManager fm, List<Fragment> mList, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ABFirstFragment tab1 = (ABFirstFragment) mList.get(position);
                return tab1;
            case 1:
                ABSecondFragment tab2 = (ABSecondFragment) mList.get(position);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
