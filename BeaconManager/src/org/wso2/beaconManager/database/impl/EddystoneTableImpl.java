package org.wso2.beaconManager.database.impl;

import org.wso2.beaconManager.database.BeaconManagerDatabase;
import org.wso2.beaconManager.database.EddystoneTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wso2123 on 11/29/16.
 */
public class EddystoneTableImpl implements EddystoneTable{
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    @Override
    public boolean updateNamespaceAndInstance(String beaconId, String namespace, String instance) {
        boolean status = false;

        try {
            conn = BeaconManagerDatabase.getConnection();
            String updateQuery = "UPDATE `eddystone` SET `namespace` = ?, " +
                    "`instance` = ? WHERE `eddystone`.`id` = ?;";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, namespace);
            pstmt.setString(2, instance);
            pstmt.setString(3, beaconId);
            pstmt.executeUpdate();

            status = true;
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        return status;
    }
}
