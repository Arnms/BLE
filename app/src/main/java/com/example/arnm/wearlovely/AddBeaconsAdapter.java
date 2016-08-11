package com.example.arnm.wearlovely;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class AddBeaconsAdapter extends FragmentPagerAdapter {
    private List<Fragment> mListFragment;

    public AddBeaconsAdapter(FragmentManager fm, List<Fragment> mListFragment) {
        super(fm);
        this.mListFragment = mListFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }
}
