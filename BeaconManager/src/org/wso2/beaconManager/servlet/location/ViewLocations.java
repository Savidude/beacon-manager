package org.wso2.beaconManager.servlet.location;

import org.wso2.beaconManager.database.LocationTable;
import org.wso2.beaconManager.database.impl.LocationTableImpl;
import org.wso2.beaconManager.util.EddystoneLocation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wso2123 on 11/29/16.
 */
@WebServlet(urlPatterns = "/location")
public class ViewLocations extends HttpServlet {
    private LocationTable locationTable;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        locationTable = new LocationTableImpl();
        List<EddystoneLocation> eddystoneLocations = locationTable.getAllEddystoneLocations();
        request.setAttribute("eddybeaconSet", getEddybeaconSet(eddystoneLocations));

        request.getRequestDispatcher("WEB-INF/views/viewLocations.jsp").forward(request, response);
    }

    private String getEddybeaconSet(List<EddystoneLocation> eddystoneLocations){
        String beaconData = "<tr>\n" +
                                "<td>%d</td>\n" +
                                "<td>%s</td>\n" +
                                "<td>%s</td>\n" +
                                "<td>%s</td>\n" +
                                "<td>\n" +
                                    "<button type=\"button\" class=\"btn btn-default\" onclick=\"location.href='/location_edit?locationId=%d';\">\n" +
                                        "<i class=\"material-icons\" style=\"font-size:18px;\">edit</i>\n" +
                                    "</button>\n" +
                                    "<button type=\"button\" class=\"btn btn-default\" onclick=\"location.href='/add_action';\">\n" +
                                        "<i class=\"material-icons\" style=\"font-size:18px;\">delete</i>\n" +
                                    "</button>\n" +
                                "</td>\n" +
                            "</tr>";
        String beaconDataSet = "";
        for(EddystoneLocation location: eddystoneLocations){
            String namespace = location.getNamespace().replace("0x", "");
            String instance = location.getInstance().replace("0x", "");
            beaconDataSet += String.format(beaconData, location.getId(), location.getName(), namespace, instance, location.getId());
        }
        return beaconDataSet;
    }
}
