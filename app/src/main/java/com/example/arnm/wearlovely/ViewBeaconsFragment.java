package com.example.arnm.wearlovely;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
        mAdapter = new BeaconListAdapter(getActivity().getApplicationContext());
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

            TextView alias = (TextView) convertView.findViewById(R.id.mbl_device_alias);
            TextView majmin = (TextView) convertView.findViewById(R.id.mbl_device_majmin);
            TextView distance = (TextView) convertView.findViewById(R.id.mbl_device_distance);

            final MyBeacon beacon = myBeaconList.get(index);

            alias.setText(beacon.getAlias());
            majmin.setText("(" + beacon.getMajor() + ", " + beacon.getMinor() + ")");
            distance.setText(String.valueOf(beaconList.get(index).getDistance()) + "m");

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BeaconAddDialog dialog = new BeaconAddDialog(mContext, BeaconAddDialog.EDIT_DIALOG_OPEN, beacon);
                    dialog.setTitle("비콘 정보 변경");
                    dialog.show();
                }
            });

            ImageButton delBtn = (ImageButton) convertView.findViewById(R.id.mbl_del_button);
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BeaconAddDialog dialog = new BeaconAddDialog(mContext, BeaconAddDialog.DEL_DIALOG_OPEN, beacon);
                    dialog.setTitle("비콘 정보 삭제");
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
