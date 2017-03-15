package org.wso2.beaconManager.database.impl;

import org.wso2.beaconManager.database.BeaconManagerDatabase;
import org.wso2.beaconManager.database.LocationTable;
import org.wso2.beaconManager.util.EddystoneLocation;

import javax.naming.ldap.PagedResultsControl;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wso2123 on 11/7/16.
 */
public class LocationTableImpl implements LocationTable{
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public Map<Integer, String> getAllLocations() {
        Map<Integer, String> locations = new HashMap<>();
        try {
            conn = BeaconManagerDatabase.getConnection();
            String selectQuery = "SELECT id, name FROM location";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectQuery);

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");

                locations.put(id, name);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }finally {
            BeaconManagerDatabase.closeResultSet(rs);
            BeaconManagerDatabase.closeStatement(stmt);
            BeaconManagerDatabase.closeConnection(conn);
        }


        return locations;
    }

    @Override
    public List<EddystoneLocation> getAllEddystoneLocations() {
        List<EddystoneLocation> eddystoneLocations = new ArrayList<>();

        try {
            conn = BeaconManagerDatabase.getConnection();
            String selectQuery = "SELECT location.id, location.name, eddystone.namespace, eddystone.instance " +
                    "FROM location, beacon_location, eddystone WHERE location.id=beacon_location.locationId " +
                    "AND eddystone.id=beacon_location.beaconId";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectQuery);

            while (rs.next()){
                EddystoneLocation location = new EddystoneLocation();
                location.setId(rs.getInt("id"));
                location.setName(rs.getString("name"));
                location.setNamespace(rs.getString("namespace"));
                location.setInstance(rs.getString("instance"));

                eddystoneLocations.add(location);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }finally {
            BeaconManagerDatabase.closeResultSet(rs);
            BeaconManagerDatabase.closeStatement(stmt);
            BeaconManagerDatabase.closeConnection(conn);
        }

        return eddystoneLocations;
    }

    @Override
    public EddystoneLocation getEddystoneLocationData(int locationId) {
        EddystoneLocation location = new EddystoneLocation();

        try {
            conn = BeaconManagerDatabase.getConnection();
            String selectQuery = "SELECT location.id, location.name, eddystone.namespace, eddystone.instance " +
                    "FROM location, beacon_location, eddystone WHERE location.id=beacon_location.locationId " +
                    "AND eddystone.id=beacon_location.beaconId AND location.id=?";
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setInt(1, locationId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                location.setId(rs.getInt("id"));
                location.setName(rs.getString("name"));
                location.setNamespace(rs.getString("namespace"));
                location.setInstance(rs.getString("instance"));
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        } finally {
            BeaconManagerDatabase.closeResultSet(rs);
            BeaconManagerDatabase.closePreparedStatement(pstmt);
            BeaconManagerDatabase.closeConnection(conn);
        }

        return location;
    }

    @Override
    public boolean updateLocationId(int oldId, int newId) {
        boolean status = false;

        try {
            conn = BeaconManagerDatabase.getConnection();
            String updateQuery = "UPDATE `location` SET `id` = ? WHERE `location`.`id` = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setInt(1, newId);
            pstmt.setInt(2, oldId);
            pstmt.executeUpdate();
            status = true;
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean updateLocationName(int id, String newName) {
        boolean status = false;

        try {
            conn = BeaconManagerDatabase.getConnection();
            String updateQuery = "UPDATE `location` SET `name` = ? WHERE `location`.`id` = ?;";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            status = true;
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        return status;
    }
}
