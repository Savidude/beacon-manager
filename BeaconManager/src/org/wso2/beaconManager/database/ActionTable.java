package org.wso2.beaconManager.database;

import org.wso2.beaconManager.util.Action;

/**
 * Created by wso2123 on 11/7/16.
 */
public interface ActionTable {

    boolean addAction(Action action);
    boolean updateLocationId(int oldId, int newId);
}
