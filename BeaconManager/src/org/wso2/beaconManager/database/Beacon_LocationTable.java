package org.wso2.beaconManager.database;

/**
 * Created by wso2123 on 11/29/16.
 */
public interface Beacon_LocationTable {

    boolean updateLocationId(int oldId, int newId);
    String getBeaconId(int locationId);
}
