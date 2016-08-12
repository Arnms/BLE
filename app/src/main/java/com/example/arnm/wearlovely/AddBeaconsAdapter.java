package com.example.arnm.wearlovely;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2016-08-11.
 */
public class AddBeaconsAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public AddBeaconsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ABFirstFragment tab1 = new ABFirstFragment();
                return tab1;
            case 1:
                ABSecondFragment tab2 = new ABSecondFragment();
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
