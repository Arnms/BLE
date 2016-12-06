package com.example.arnm.wearlovely;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arnm on 2016-11-01.
 */

public class BeaconInfoDialog extends Dialog {
    public static int EDIT_DIALOG_OPEN = 200;
    public static int DEL_DIALOG_OPEN = 400;

    private int isInfo;
    private Beacon beacon;
    private MyBeacon myBeacon;

    private EditText mViewMajor;
    private EditText mViewMinor;
    private EditText mViewAlias;
    private Button mButtonOK;
    private Button mButtonCancel;
    private TableRow mButtonTable;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == PostCode.REQUEST_DEL_BEACON_CODE) {
                Toast.makeText(getOwnerActivity().getApplicationContext(), "비콘을 삭제했습니다.", Toast.LENGTH_SHORT);
            }
        }
    };

    public BeaconInfoDialog(Context mContext, int isInfo, Beacon beacon) {
        super(mContext);
        this.isInfo = isInfo;
        this.beacon = beacon;
    }

    public BeaconInfoDialog(Context mContext, int isInfo, MyBeacon myBeacon) {
        super(mContext);
        this.isInfo = isInfo;
        this.myBeacon = myBeacon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.info_beacon_dialog);

        mViewMajor = (EditText) findViewById(R.id.ibd_view_major);
        mViewMinor = (EditText) findViewById(R.id.ibd_view_minor);
        mViewAlias = (EditText) findViewById(R.id.ibd_view_alias);
        mButtonOK = (Button) findViewById(R.id.del_ok);
        mButtonCancel = (Button) findViewById(R.id.del_cancel);
        mButtonTable = (TableRow) findViewById(R.id.ibd_button_table);

        mViewMajor.setText(myBeacon.getMajor().toString());
        mViewMinor.setText(myBeacon.getMinor().toString());
        mViewAlias.setText(myBeacon.getAlias().toString());

        if(isInfo == DEL_DIALOG_OPEN) {
            mViewMajor.setFocusable(false);
            mViewMajor.setClickable(false);
            mViewMinor.setFocusable(false);
            mViewMinor.setClickable(false);
            mViewAlias.setFocusable(false);
            mViewAlias.setClickable(false);
        } else {
            mViewMajor.setFocusable(true);
            mViewMajor.setClickable(true);
            mViewMinor.setFocusable(true);
            mViewMinor.setClickable(true);
            mViewAlias.setFocusable(true);
            mViewAlias.setClickable(true);
        }

        setButtonListener();
    }

    public void setButtonListener() {
        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInfo == DEL_DIALOG_OPEN) {
                    try {
                        String url = "/beacon/del";
                        JSONObject obj = new JSONObject();
                        obj.put("_id", myBeacon.get_id());

                        SendPost sp = new SendPost(handler, obj, url, PostCode.REQUEST_DEL_BEACON_CODE);
                        Thread t = new Thread(sp);
                        t.start();
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                } else if(isInfo == EDIT_DIALOG_OPEN) {
                    try {
                        String url = "/beacon/edit";
                        JSONObject obj = new JSONObject();
                        obj.put("uuid", myBeacon.getUuid().toString());
                        obj.put("major", myBeacon.getMajor().toString());
                        obj.put("minor", myBeacon.getMinor().toString());
                        obj.put("alias", mViewAlias.getText().toString());

                        SendPost sp = new SendPost(handler, obj, url, PostCode.REQUEST_EDIT_BEACON_CODE);
                        Thread t = new Thread(sp);
                        t.start();
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }

                BeaconInfoDialog.super.dismiss();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeaconInfoDialog.super.cancel();
            }
        });
    }
}
