package com.example.arnm.wearlovely;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016-07-15.
 */
public class BeaconListAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<Beacon> beaconList = new ArrayList<Beacon>();
    private static LayoutInflater inflater = null;

    public BeaconListAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(beaconList.isEmpty()) return 0;
        else return beaconList.size();
    }

    @Override
    public Object getItem(int index) {
        return beaconList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, null);
        }

        TextView major = (TextView) convertView.findViewById(R.id.li_device_major);
        TextView minor = (TextView) convertView.findViewById(R.id.li_device_minor);
        TextView distance = (TextView) convertView.findViewById(R.id.li_device_distance);
        TextView rssi = (TextView) convertView.findViewById(R.id.li_device_rssi);

        Beacon beacon = beaconList.get(index);

        if(beacon != null) {
            major.setText("Major : " + beacon.getId2().toString());
            minor.setText("Minor : " + beacon.getId3().toString());
            distance.setText("거리 : " + Double.parseDouble(String.format("%.3f", beacon.getDistance())));
        }

        if(beacon.getRssi() >= -59) {
            //rssi.setTextColor(mContext.getResources().getColor(R.color.blue_900));
            rssi.setText("신호 세기 : 강함");
        } else if(beacon.getRssi() >= -70) {
            //rssi.setTextColor(mContext.getResources().getColor(R.color.yellow_A700));
            rssi.setText("신호 세기 : 보통");
        } else {
            //rssi.setTextColor(mContext.getResources().getColor(R.color.red_900));
            rssi.setText("신호 세기 : 약함");
        }

        return convertView;
    }

    public void setBeaconList(List<Beacon> list) {
        beaconList = list;
    }

    public void initAll(Collection<Beacon> newBeacons) {
        this.beaconList.clear();
        this.beaconList.addAll(newBeacons);
        this.notifyDataSetChanged();
    }
}
