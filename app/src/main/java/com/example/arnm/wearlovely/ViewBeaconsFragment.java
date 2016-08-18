package com.example.arnm.wearlovely;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016-08-09.
 */
public class ViewBeaconsFragment extends Fragment {
    private ListView mListView;
    private TextView mNoneText;
    private BeaconListAdapter mAdapter;
    private List<MyBeacon> myBeaconList;

    public ViewBeaconsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new BeaconListAdapter(getActivity());
        myBeaconList = new ArrayList<>();
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

        if (myBeaconList.size() == 0) {
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
                convertView = inflater.inflate(R.layout.my_beacon_list, null);
            }

            ImageView conState = (ImageView) convertView.findViewById(R.id.mbl_connect_state_image);
            TextView major = (TextView) convertView.findViewById(R.id.mbl_device_major);
            TextView minor = (TextView) convertView.findViewById(R.id.mbl_device_minor);
            TextView connect = (TextView) convertView.findViewById(R.id.mbl_device_connect);
            TextView alias = (TextView) convertView.findViewById(R.id.mbl_beacon_alias);

            MyBeacon beacon = myBeaconList.get(index);

            major.setText(beacon.getMajor());
            minor.setText(beacon.getMinor());
            alias.setText(beacon.getAlias());

            if(beacon.isEquals(beaconList)) {
                conState.setAlpha(1.0f);
                connect.setText("연결 됨");
            } else {
                conState.setAlpha(0f);
                connect.setText("연결 끊김");
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
}
