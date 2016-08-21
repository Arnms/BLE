package com.example.arnm.wearlovely;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements RangeNotifier, BeaconConsumer, NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private SettingDialog mSettingDialog;

    private BeaconManager mBeaconManager;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mFragment;
    private int mPosition;

    private Region mRegion = new Region("Wearlovely", Identifier.parse("617E8096-BAB7-43F3-BF96-3FD6F26D67B1"), null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_beacon_list);

        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        mBeaconManager.bind(this);

        if(savedInstanceState == null) {
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragment = new ViewBeaconsFragment();
            mFragmentTransaction.add(R.id.cm_view_fragment, mFragment);
            mFragmentTransaction.commit();
        }

        mPosition = R.id.cm_view_fragment;
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            settingDialogView();
        } else if(id == R.id.action_add && id != mPosition) {
            mPosition = id;
            navigationView.setCheckedItem(R.id.nav_beacon_add);
            mFragment = new AddBeaconsFragment();
        } else if(id == R.id.action_list && id != mPosition) {
            mPosition = id;
            navigationView.setCheckedItem(R.id.nav_beacon_list);
            mFragment = new ViewBeaconsFragment();
        }

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.cm_view_fragment, mFragment);
        mFragmentTransaction.commit();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mPosition = id;

        if (id == R.id.nav_beacon_list) {
            mFragment = new ViewBeaconsFragment();
        } else if (id == R.id.nav_beacon_add) {
            mFragment = new AddBeaconsFragment();
        } else if (id == R.id.nav_scanning_state) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.cm_view_fragment, mFragment);
        mFragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mFragment.getClass() == ViewBeaconsFragment.class){
                    ((ViewBeaconsFragment) mFragment).refreshOnListView(beacons);
                } else if(mFragment.getClass() == AddBeaconsFragment.class){
                    ((AddBeaconsFragment) mFragment).refreshOnListView(beacons);
                }
            }
        });
    }

    public void settingDialogView() {
        String maj, min;

        if(mRegion.getId2() == null) {
            maj = "";
        } else {
            maj = mRegion.getId2().toString();
        }

        if(mRegion.getId3() == null) {
            min = "";
        } else {
            min = mRegion.getId3().toString();
        }

        mSettingDialog = new SettingDialog(this, "비콘 스캔 설정", maj, min, okListener);
        mSettingDialog.show();
    }

    private View.OnClickListener okListener = new View.OnClickListener() {
        public void onClick(View v) {
            /*Identifier major, minor;

            if(mSettingDialog.getmMajor().isEmpty()) {
                major = null;
            } else {
                major = Identifier.parse(mSettingDialog.getmMajor());
            }

            if(mSettingDialog.getmMinor().isEmpty()) {
                minor = null;
            } else {
                minor = Identifier.parse(mSettingDialog.getmMinor());
            }

            mRegion = new Region("Wearlovely", Identifier.parse("617E8096-BAB7-43F3-BF96-3FD6F26D67B1"), major, minor);
*/
            Toast.makeText(getApplicationContext(), "비콘 스캔 범위를 변경했습니다.", Toast.LENGTH_SHORT).show();
            mSettingDialog.dismiss();
        }
    };
}
