package com.example.arnm.wearlovely;

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

public class SigninFragment extends Fragment {
    private JSONObject jsonObject;
    private EditText username;
    private EditText useremail;
    private EditText password;

    public SigninFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        username = (EditText) view.findViewById(R.id.signin_username);
        useremail = (EditText) view.findViewById(R.id.signin_useremail);
        password = (EditText) view.findViewById(R.id.signin_password);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jsonObject = new JSONObject();
    }

    public String getUsername() { return username.getText().toString(); }

    public String getUseremail() { return useremail.getText().toString(); }

    public String getPassword() { return password.getText().toString(); }
}
