package org.wso2.beaconManager.database;

/**
 * Created by wso2123 on 11/29/16.
 */
public interface EddystoneTable {

    boolean updateNamespaceAndInstance(String beaconId, String namespace, String instance);
}
