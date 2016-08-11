package com.example.arnm.wearlovely;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;

/**
 * Created by Administrator on 2016-08-09.
 */
public class ViewBeaconsFragment extends Fragment {
    private ListView mListView;
    private TextView mNoneText;
    private BeaconListAdapter mAdapter;

    public ViewBeaconsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new BeaconListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_beacon, container, false);
        mListView = (ListView) view.findViewById(R.id.fvb_device_list);
        mNoneText = (TextView) view.findViewById(R.id.fvb_none_text);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setAdapter(mAdapter);
        setHasOptionsMenu(true);
    }

    public void refreshOnListView(Collection<Beacon> beacons) {
        mAdapter.initAll(beacons);

        if (mAdapter.getCount() == 0) {
            mListView.setVisibility(View.GONE);
            mNoneText.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            mNoneText.setVisibility(View.GONE);
        }
    }
}
