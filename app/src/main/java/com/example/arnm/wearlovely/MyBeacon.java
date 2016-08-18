package com.example.arnm.wearlovely;

import org.altbeacon.beacon.Beacon;

import java.util.List;

/**
 * Created by Administrator on 2016-08-18.
 */
public class MyBeacon {
    private String uuid;
    private String major;
    private String minor;
    private String alias;

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

    public boolean isEquals(List<Beacon> beacons) {
        for(Beacon b : beacons) {
            if(this.uuid.equals(b.getId1()) && this.major.equals(b.getId2()) && this.minor.equals(b.getId3())) {
                return true;
            }
        }

        return false;
    }
}
