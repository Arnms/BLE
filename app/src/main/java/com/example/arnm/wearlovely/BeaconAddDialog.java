package com.example.arnm.wearlovely;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arnm on 2016-08-27.
 */

public class BeaconAddDialog extends Dialog {
    public static int ADD_DIALOG_OPEN = 100;
    public static int EDIT_DIALOG_OPEN = 200;
    public static int INFO_DIALOG_OPEN = 300;
    public static int DEL_DIALOG_OPEN = 400;

    private int isAdd;
    private Beacon beacon;
    private MyBeacon myBeacon;

    private TextView mViewMajor;
    private TextView mViewMinor;
    private EditText mViewAlias;
    private Button mButtonOK;
    private Button mButtonCancel;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == PostCode.REQUEST_ADD_BEACON_CODE) {
                Toast.makeText(getContext().getApplicationContext(), "비콘을 등록했습니다.", Toast.LENGTH_SHORT);
            }
        }
    };

    public BeaconAddDialog(Context mContext, int isAdd, Beacon beacon) {
        super(mContext);
        this.isAdd = isAdd;
        this.beacon = beacon;
    }

    public BeaconAddDialog(Context mContext, int isAdd, MyBeacon myBeacon) {
        super(mContext);
        this.isAdd = isAdd;
        this.myBeacon = myBeacon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.add_button_dialog);

        mViewMajor = (TextView) findViewById(R.id.abd_view_major);
        mViewMinor = (TextView) findViewById(R.id.abd_view_minor);
        mViewAlias = (EditText) findViewById(R.id.abd_view_alias);
        mButtonOK = (Button) findViewById(R.id.add_ok);
        mButtonCancel = (Button) findViewById(R.id.add_cancel);

        mViewMajor.setText(beacon.getId2().toString());
        mViewMinor.setText(beacon.getId3().toString());

        if(isAdd != ADD_DIALOG_OPEN && isAdd != INFO_DIALOG_OPEN) {
            mViewAlias.setText(myBeacon.getAlias());
        }

        if(isAdd == DEL_DIALOG_OPEN) {
            mViewAlias.setFocusable(false);
            mViewAlias.setClickable(false);
        }

        setButtonListener();
    }

    public void setButtonListener() {
        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAdd == ADD_DIALOG_OPEN) {
                    try {
                        String url = "http://192.168.209.155:3000/beacon/add";
                        JSONObject obj = new JSONObject();
                        obj.put("uuid", beacon.getId1().toString());
                        obj.put("major", beacon.getId2().toString());
                        obj.put("minor", beacon.getId3().toString());
                        obj.put("alias", mViewAlias.getText().toString());

                        SendPost sp = new SendPost(handler, obj, url, PostCode.REQUEST_ADD_BEACON_CODE);
                        Thread t = new Thread(sp);
                        t.start();
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }

                BeaconAddDialog.super.dismiss();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeaconAddDialog.super.cancel();
            }
        });
    }
}
