package com.example.arnm.wearlovely;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-08-18.
 */
public class MyBeacon implements Serializable {
    private String _id;
    private String uuid;
    private String major;
    private String minor;
    private String alias;

    public MyBeacon() {}

    public MyBeacon(String _id, String uuid, String major, String minor, String alias) {
        this._id = _id;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.alias = alias;
    }

    public String get_id() { return _id; }

    public void set_id(String _id) { this._id = _id; }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isEquals(Beacon beacon) {
        if(Identifier.parse(uuid).equals(beacon.getId1()) && Identifier.parse(major).equals(beacon.getId2()) && Identifier.parse(minor).equals(beacon.getId3())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEquals(List<Beacon> beacons) {
        for(Beacon b : beacons) {
            if(Identifier.parse(uuid).equals(b.getId1()) && Identifier.parse(major).equals(b.getId2()) && Identifier.parse(minor).equals(b.getId3())) {
                return true;
            }
        }

        return false;
    }

    public MyBeacon toJSONParse(JSONObject obj) {
        MyBeacon beacon = null;

        try {
            beacon = new MyBeacon(obj.getString("_id"), obj.getString("uuid"), obj.getString("major"), obj.getString("minor"), obj.getString("alias"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return beacon;
    }
}
