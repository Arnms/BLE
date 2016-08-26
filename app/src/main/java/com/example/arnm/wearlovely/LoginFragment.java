package com.example.arnm.wearlovely;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONObject;

/**
 * Created by Arnm on 2016-08-24.
 */

public class LoginFragment extends Fragment {
    private JSONObject jsonObject;
    private EditText useremail;
    private EditText password;

    public LoginFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        useremail = (EditText) view.findViewById(R.id.lg_useremail);
        password = (EditText) view.findViewById(R.id.lg_password);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jsonObject = new JSONObject();
    }

    public String getUseremail() { return useremail.getText().toString(); }

    public String getPassword() { return password.getText().toString(); }
}
