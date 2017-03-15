package org.wso2.beaconManager.database.impl;

import org.wso2.beaconManager.database.BeaconManagerDatabase;
import org.wso2.beaconManager.database.Beacon_LocationTable;

import java.sql.*;

/**
 * Created by wso2123 on 11/29/16.
 */
public class Beacon_LocationTableImpl implements Beacon_LocationTable{
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public boolean updateLocationId(int oldId, int newId) {
        boolean status = false;

        try {
            conn = BeaconManagerDatabase.getConnection();
            String updateQuery = "UPDATE `beacon_location` SET `locationId` = ? WHERE `beacon_location`.`locationId` = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setInt(1, newId);
            pstmt.setInt(2, oldId);
            pstmt.executeUpdate();
            status = true;
        } catch (SQLException e) {
//            e.printStackTrace();
        } finally {
            BeaconManagerDatabase.closePreparedStatement(pstmt);
            BeaconManagerDatabase.closeConnection(conn);
        }

        return status;
    }

    @Override
    public String getBeaconId(int locationId) {
        String beaconid = null;

        try {
            conn = BeaconManagerDatabase.getConnection();
            String selectQuery = "SELECT beaconId FROM beacon_location WHERE locationId=?";
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setInt(1, locationId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                beaconid = rs.getString("beaconId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BeaconManagerDatabase.closeResultSet(rs);
            BeaconManagerDatabase.closePreparedStatement(pstmt);
            BeaconManagerDatabase.closeConnection(conn);
        }

        return beaconid;
    }
}
