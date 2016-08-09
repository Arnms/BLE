package com.example.arnm.wearlovely;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * Created by Administrator on 2016-08-09.
 */
public class ViewBeacons extends Fragment implements RangeNotifier, BeaconConsumer {
    private ListView mListView;
    private TextView mNoneText;
    private DeviceListAdapter mAdapter;
    private BeaconManager mBeaconManager;

    private final Region mRegion = new Region("Wearlovely", null, null, null);

    public ViewBeacons() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBeaconManager = BeaconManager.getInstanceForApplication(getActivity());
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        mAdapter = new DeviceListAdapter(getActivity());
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
        mBeaconManager.bind(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if(mBeaconManager.isBound(this)){
            mBeaconManager.setBackgroundMode(false);
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        /*if(mBeaconManager.isBound(this)){
            mBeaconManager.setBackgroundMode(true);
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        try {
            mBeaconManager.startRangingBeaconsInRegion(mRegion);
        }catch(RemoteException e) {
            e.printStackTrace();
        }

        mBeaconManager.setRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.initAll(beacons);

                if(mAdapter.getCount() == 0) {
                    mListView.setVisibility(View.GONE);
                    mNoneText.setVisibility(View.VISIBLE);
                } else {
                    mListView.setVisibility(View.VISIBLE);
                    mNoneText.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int mode) {
        return getActivity().bindService(intent, serviceConnection, mode);
    }
}
