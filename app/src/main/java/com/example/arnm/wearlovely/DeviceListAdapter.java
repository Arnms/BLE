package com.example.arnm.wearlovely;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.List;

/**
 * Created by Administrator on 2016-07-15.
 */
public class DeviceListAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<Beacon> beaconList;
    private static LayoutInflater inflater = null;

    public DeviceListAdapter(Context mContext, List<Beacon> beaconList) {
        this.mContext = mContext;
        this.beaconList = beaconList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return beaconList.size();
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

        TextView mNoneText = (TextView) ((Activity) mContext).findViewById(R.id.cm_none_text);
        ListView mListView = (ListView) ((Activity) mContext).findViewById(R.id.cm_device_list);

        if(getCount() == 0) {
            mNoneText.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        } else {
            mNoneText.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
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

    public void addItem(Beacon b) {
        beaconList.add(b);
    }
}
