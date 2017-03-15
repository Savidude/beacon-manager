package org.wso2.beaconManager.database;

import org.wso2.beaconManager.util.EddystoneLocation;

import java.util.List;
import java.util.Map;

/**
 * Created by wso2123 on 11/7/16.
 */
public interface LocationTable {

    Map<Integer, String> getAllLocations();
    List<EddystoneLocation> getAllEddystoneLocations();
    EddystoneLocation getEddystoneLocationData(int locationId);
    boolean updateLocationId(int oldId, int newId);
    boolean updateLocationName(int id, String newName);
}
