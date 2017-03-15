package org.wso2.beaconManager.servlet.location;

import org.wso2.beaconManager.database.ActionTable;
import org.wso2.beaconManager.database.Beacon_LocationTable;
import org.wso2.beaconManager.database.EddystoneTable;
import org.wso2.beaconManager.database.LocationTable;
import org.wso2.beaconManager.database.impl.ActionTableImpl;
import org.wso2.beaconManager.database.impl.Beacon_LocationTableImpl;
import org.wso2.beaconManager.database.impl.EddystoneTableImpl;
import org.wso2.beaconManager.database.impl.LocationTableImpl;
import org.wso2.beaconManager.util.EddystoneLocation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wso2123 on 11/29/16.
 */
@WebServlet(urlPatterns = "/location_edit")
public class EditLocation extends HttpServlet {
    private LocationTable locationTable;
    private Beacon_LocationTable beacon_locationTable;
    private ActionTable actionTable;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String namespace = request.getParameter("namespace");
        namespace = "0x" + namespace;
        String instance = request.getParameter("instance");
        instance = "0x" + instance;

        int oldId = Integer.parseInt(request.getParameter("oldId"));

        locationTable = new LocationTableImpl();
        beacon_locationTable = new Beacon_LocationTableImpl();
        actionTable = new ActionTableImpl();

        if(id!=oldId){
            locationTable.updateLocationId(oldId, id);
            beacon_locationTable.updateLocationId(oldId, id);
            actionTable.updateLocationId(oldId, id);
        }

        locationTable.updateLocationName(id, name);

        String beaconId = beacon_locationTable.getBeaconId(id);
        EddystoneTable eddystoneTable = new EddystoneTableImpl();
        eddystoneTable.updateNamespaceAndInstance(beaconId, namespace, instance);

        request.getRequestDispatcher("WEB-INF/views/viewLocations.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        LocationTable locationTable = new LocationTableImpl();
        EddystoneLocation location = locationTable.getEddystoneLocationData(locationId);
        request.setAttribute("id", location.getId());
        request.setAttribute("name", location.getName());
        request.setAttribute("namespace", location.getNamespace().replace("0x", ""));
        request.setAttribute("instance", location.getInstance().replace("0x", ""));

        request.setAttribute("oldId", location.getId());

        request.getRequestDispatcher("WEB-INF/views/editLocation.jsp").forward(request, response);
    }
}
