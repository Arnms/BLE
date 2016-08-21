package com.example.arnm.wearlovely;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2016-08-21.
 */
public class SettingDialog extends Dialog {
    private String mMajor;
    private String mMinor;
    private String mTitle;
    private View.OnClickListener mOkClickListener;

    private EditText mSettingMajor;
    private EditText mSettingMinor;
    private Button mDialogOk;

    public SettingDialog(Context context, String title, String major, String minor, View.OnClickListener okClickListener) {
        super(context);
        this.mMajor = major;
        this.mMinor = minor;
        this.mTitle = title;
        this.mOkClickListener = okClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.settings_dialog);

        mSettingMajor = (EditText) findViewById(R.id.settings_major);
        mSettingMinor = (EditText) findViewById(R.id.settings_minor);
        mDialogOk = (Button) findViewById(R.id.dialog_ok);

        this.setTitle(mTitle);
        mSettingMajor.setText(mMajor);
        mSettingMinor.setText(mMinor);

        if(mOkClickListener != null) {
            mDialogOk.setOnClickListener(mOkClickListener);
        }
    }

    public String getmMajor() {
        return this.mMajor;
    }

    public String getmMinor() {
        return this.mMinor;
    }
}
