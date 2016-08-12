package com.example.arnm.wearlovely;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;

/**
 * Created by Administrator on 2016-08-11.
 */
public class ABSecondFragment extends Fragment {

    public ABSecondFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_second, container, false);
    }
}
