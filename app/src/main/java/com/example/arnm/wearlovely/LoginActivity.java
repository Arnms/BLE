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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mFragment;

    private LinearLayout mLoginButtons;
    private LinearLayout mSigninButtons;
    private TextView mLoginTitle;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == PostCode.REQUEST_SIGNIN_CODE) {
                msgSignin(msg.obj);
            } else if(msg.what == PostCode.REQUEST_LOGIN_CODE) {
                msgLogin(msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mLoginButtons = (LinearLayout) findViewById(R.id.login_buttons);
        mSigninButtons = (LinearLayout) findViewById(R.id.signin_buttons);
        mLoginTitle = (TextView) findViewById(R.id.login_title);

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
        /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();*/
        String useremail = ((LoginFragment) mFragment).getUseremail();
        String password = ((LoginFragment) mFragment).getPassword();

        try {
            String url = "/login";
            JSONObject obj = new JSONObject();
            obj.put("useremail", useremail);
            obj.put("password", password);

            SendPost sp = new SendPost(handler, obj, url, PostCode.REQUEST_LOGIN_CODE);
            Thread t = new Thread(sp);
            t.start();
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick_lg_signin(View v) {
        toSigninFragment();
    }

    public void toSigninFragment() {
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
                mLoginTitle.setText("회원가입");
            }
        });
    }

    public void onClick_signin_ok(View v) {
        String username = ((SigninFragment) mFragment).getUsername();
        String useremail = ((SigninFragment) mFragment).getUseremail();
        String password = ((SigninFragment) mFragment).getPassword();

        try {
            String url = "/users";
            JSONObject obj = new JSONObject();
            obj.put("username", username);
            obj.put("useremail", useremail);
            obj.put("password", password);

            SendPost sp = new SendPost(handler, obj, url, PostCode.REQUEST_SIGNIN_CODE);
            Thread t = new Thread(sp);
            t.start();
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick_signin_cancel(View v) {
        toLoginFragment();
    }

    public void toLoginFragment() {
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
                mLoginTitle.setText("로그인");
            }
        });
    }

    public void msgSignin(Object obj) {
        try {
            JSONObject jsonObject = (JSONObject) obj;
            int result = (int) jsonObject.get("result");

            if (result == 0) {
                Toast.makeText(getApplicationContext(), "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                toLoginFragment();
            } else if (result == 1) {
                Toast.makeText(getApplicationContext(), "회원가입 과정에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void msgLogin(Object obj) {
        try {
            JSONObject jsonObject = (JSONObject) obj;
            int result = (int) jsonObject.get("result");

            if (result == 0) {
                if(((JSONObject) obj).isNull("user")) {
                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    MyUser user = new MyUser().toJSONParse(((JSONObject) obj).getJSONObject("user"));
                    Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    this.finish();
                }
            } else if (result == 1) {
                Toast.makeText(getApplicationContext(), "로그인 과정에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
