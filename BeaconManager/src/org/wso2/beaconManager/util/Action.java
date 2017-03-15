package org.wso2.beaconManager.util;

/**
 * Created by wso2123 on 11/7/16.
 */
public class Action {
    private int profileId;
    private int locationId;
    private String type;
    private String value;

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getProfileId() {
        return profileId;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
