package com.example.arnm.wearlovely;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Arnm on 2016-08-27.
 */

public class MyUser implements Serializable {
    private String _id;
    private String username;
    private String useremail;
    private String password;

    public MyUser() { }

    public MyUser(String _id, String username, String useremail, String password) {
        this._id = _id;
        this.username = username;
        this.useremail = useremail;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MyUser toJSONParse(JSONObject obj) {
        MyUser user = null;

        try {
            user = new MyUser(obj.getString("_id"), obj.getString("username"), obj.getString("useremail"), obj.getString("password"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}
