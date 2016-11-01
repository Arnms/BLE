package com.example.arnm.wearlovely;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class ABSecondFragment extends Fragment {
    private ListView mListView;
    private TextView mNoneText;
    private BeaconListAdapter mAdapter;

    public ABSecondFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new BeaconListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_second, container, false);
        mListView = (ListView) view.findViewById(R.id.fas_device_list);
        mNoneText = (TextView) view.findViewById(R.id.fas_none_text);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Adapter Setting
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

    private class BeaconListAdapter extends BaseAdapter {
        private Context mContext = null;
        private List<Beacon> beaconList = new ArrayList<Beacon>();
        private LayoutInflater inflater = null;
        private BeaconAddDialog mBeaconAddDialog;

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
                convertView = inflater.inflate(R.layout.surround_beacon_list, null);
            }

            TextView major = (TextView) convertView.findViewById(R.id.sbl_device_major);
            TextView minor = (TextView) convertView.findViewById(R.id.sbl_device_minor);
            TextView distance = (TextView) convertView.findViewById(R.id.sbl_device_distance);
            TextView rssi = (TextView) convertView.findViewById(R.id.sbl_device_rssi);

            final Beacon beacon = beaconList.get(index);

            if(beacon != null) {
                major.setText(beacon.getId2().toString());
                minor.setText(beacon.getId3().toString());
                distance.setText(Double.parseDouble(String.format("%.3f", beacon.getDistance())) + "m");
            }

            if(beacon.getRssi() >= -59) {
                rssi.setTextColor(mContext.getResources().getColor(R.color.blue_900));
                rssi.setText("강함");
            } else if(beacon.getRssi() >= -70) {
                rssi.setTextColor(mContext.getResources().getColor(R.color.yellow_A700));
                rssi.setText("보통");
            } else {
                rssi.setTextColor(mContext.getResources().getColor(R.color.red_900));
                rssi.setText("약함");
            }

            LinearLayout info = (LinearLayout) convertView.findViewById(R.id.sbl_beacon_info);
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BeaconAddDialog dialog = new BeaconAddDialog(mContext, BeaconAddDialog.INFO_DIALOG_OPEN, beacon);
                    dialog.setTitle("비콘 정보 확인");
                    dialog.show();
                }
            });

            ImageButton addBtn = (ImageButton) convertView.findViewById(R.id.sbl_add_button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BeaconAddDialog dialog = new BeaconAddDialog(mContext, BeaconAddDialog.ADD_DIALOG_OPEN, beacon);
                    dialog.setTitle("비콘 정보 등록");
                    dialog.show();
                }
            });

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
}
