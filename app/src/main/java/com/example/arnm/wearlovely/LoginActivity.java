package com.example.arnm.wearlovely;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mFragment;

    private LinearLayout mLoginButtons;
    private LinearLayout mSigninButtons;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 0) {
                    JSONObject obj = (JSONObject) msg.obj;
                    Log.d("test", obj.get("_id").toString());
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mLoginButtons = (LinearLayout) findViewById(R.id.login_buttons);
        mSigninButtons = (LinearLayout) findViewById(R.id.signin_buttons);

        if(savedInstanceState == null) {
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragment = new LoginFragment();
            mFragmentTransaction.add(R.id.login_view_fragment, mFragment);
            mFragmentTransaction.commit();

            mLoginButtons.setVisibility(View.VISIBLE);
            mSigninButtons.setVisibility(View.GONE);
        }
    }

    public void onClick_lg_login(View v) {
        String useremail = ((LoginFragment) mFragment).getUseremail();
        String password = ((LoginFragment) mFragment).getPassword();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onClick_lg_signin(View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFragment = new SigninFragment();
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.login_view_fragment, mFragment);
                mFragmentTransaction.commit();

                mLoginButtons.setVisibility(View.GONE);
                mSigninButtons.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onClick_signin_ok(View v) {
        String username = ((SigninFragment) mFragment).getUsername();
        String useremail = ((SigninFragment) mFragment).getUseremail();
        String password = ((SigninFragment) mFragment).getPassword();

        try {
            JSONObject obj = new JSONObject();
            obj.put("username", username);
            obj.put("useremail", useremail);
            obj.put("password", password);

            SendPost sp = new SendPost(handler, obj);
            Thread t = new Thread(sp);
            t.start();
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick_signin_cancel(View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFragment = new LoginFragment();
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.login_view_fragment, mFragment);
                mFragmentTransaction.commit();

                mLoginButtons.setVisibility(View.VISIBLE);
                mSigninButtons.setVisibility(View.GONE);
            }
        });
    }
}
