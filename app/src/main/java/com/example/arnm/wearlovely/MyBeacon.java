package com.example.arnm.wearlovely;

import org.altbeacon.beacon.Beacon;

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
    private String bluetoothAddress;
    private Date lasttime;

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
        if(this.uuid.equals(beacon.getId1()) && this.major.equals(beacon.getId2()) && this.minor.equals(beacon.getId3())) {
            return true;
        } else {
            return false;
        }
    }

    public String getBluetoothAddress() { return bluetoothAddress; }

    public void setBluetoothAddress(String bluetoothAddress) { this.bluetoothAddress = bluetoothAddress; }

    public Date getLasttime() { return lasttime; }

    public void setLasttime(Date lasttime) { this.lasttime = lasttime; }

    public boolean isEquals(List<Beacon> beacons) {
        for(Beacon b : beacons) {
            if(this.uuid.equals(b.getId1()) && this.major.equals(b.getId2()) && this.minor.equals(b.getId3())) {
                return true;
            }
        }

        return false;
    }

    public String getLastAccessTime() {
        long currentTime = System.currentTimeMillis();
        Date d = new Date(currentTime - lasttime.getTime());

        if(d.getSeconds() <= 5) {
            return "5초 이하";
        } else if(d.getMinutes() < 1) {
            return String.valueOf(d.getSeconds()) + "초 전";
        } else if(d.getHours() < 1) {
            return String.valueOf(d.getMinutes()) + "분 전";
        } else if(d.getDay() < 1) {
            return String.valueOf(d.getHours()) + "시간 전";
        } else {
            return String.valueOf(d.getTime() / 1000 / 60 / 60 / 24) + "일 전";
        }
    }
}
