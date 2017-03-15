package org.wso2.beaconManager.database.impl;

import org.wso2.beaconManager.database.ActionTable;
import org.wso2.beaconManager.database.BeaconManagerDatabase;
import org.wso2.beaconManager.util.Action;

import java.sql.*;

/**
 * Created by wso2123 on 11/7/16.
 */
public class ActionTableImpl implements ActionTable {
    private Connection conn = null;
    private Statement stmt = null;
    PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public boolean addAction(Action action) {
        boolean status = false;
        try {
            conn = BeaconManagerDatabase.getConnection();
            String insertQuery = "INSERT INTO `action` (`profileId`, `locationId`, `type`, `value`) VALUES (?, ?, ?, ?);";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, action.getProfileId());
            pstmt.setInt(2, action.getLocationId());
            pstmt.setString(3, action.getType());
            pstmt.setString(4, action.getValue());
            int rows = pstmt.executeUpdate();

            status = rows>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BeaconManagerDatabase.closePreparedStatement(pstmt);
            BeaconManagerDatabase.closeConnection(conn);
        }
        return status;
    }

    @Override
    public boolean updateLocationId(int oldId, int newId) {
        boolean status = false;

        try {
            conn = BeaconManagerDatabase.getConnection();
            String updateQuery = "UPDATE `action` SET `locationId` = ? WHERE `action`.`locationId` = ?;";
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
}
