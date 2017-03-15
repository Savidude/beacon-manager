package org.wso2.beaconManager.database.impl;

import org.wso2.beaconManager.database.BeaconManagerDatabase;
import org.wso2.beaconManager.database.ProfileTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wso2123 on 11/7/16.
 */
public class ProfileTableImpl implements ProfileTable {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    @Override
    public Map<Integer, String> getAllProfiles() {
        Map<Integer, String> profiles = new HashMap<>();
        try {
            conn = BeaconManagerDatabase.getConnection();
            String selectQuery = "SELECT id, name FROM profile";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectQuery);

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");

                profiles.put(id, name);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }finally {
            BeaconManagerDatabase.closeResultSet(rs);
            BeaconManagerDatabase.closeStatement(stmt);
            BeaconManagerDatabase.closeConnection(conn);
        }


        return profiles;
    }
}
