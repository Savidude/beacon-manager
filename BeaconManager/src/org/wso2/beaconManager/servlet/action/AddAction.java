package org.wso2.beaconManager.servlet.action;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.wso2.beaconManager.constants.Constants;
import org.wso2.beaconManager.database.ActionTable;
import org.wso2.beaconManager.database.LocationTable;
import org.wso2.beaconManager.database.ProfileTable;
import org.wso2.beaconManager.database.impl.ActionTableImpl;
import org.wso2.beaconManager.database.impl.LocationTableImpl;
import org.wso2.beaconManager.database.impl.ProfileTableImpl;
import org.wso2.beaconManager.util.Action;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@WebServlet(urlPatterns = "/add_action")
public class AddAction extends HttpServlet {
    private ProfileTable profileTable;
    private LocationTable locationTable;
    private ActionTable actionTable;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        profileTable = new ProfileTableImpl();
        Map<Integer, String> profiles = profileTable.getAllProfiles();
        request.setAttribute("profileSet", getOptions(profiles));

        locationTable = new LocationTableImpl();
        Map<Integer, String> locations = locationTable.getAllLocations();
        request.setAttribute("locationSet", getOptions(locations));

        request.getRequestDispatcher("WEB-INF/views/addAction.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int profileId = Integer.parseInt(request.getParameter("profile"));
        int locationId = Integer.parseInt(request.getParameter("location"));
        String type = request.getParameter("action");

        actionTable = new ActionTableImpl();

        boolean status = false;
        Action action = new Action();
        action.setProfileId(profileId);
        action.setLocationId(locationId);
        action.setType(type);

        switch (type){
            case Constants.TYPE_IMAGE: {

                break;
            }
            case Constants.TYPE_URL: {
                action.setValue(request.getParameter("value"));
                status = actionTable.addAction(action);
                break;
            }
            case Constants.TYPE_ENDPOINT: {
                String requestType = request.getParameter("requestType");
                String requestUrl = request.getParameter("requestUrl");

                String[] keys = request.getParameterValues("keys");
                String[] values = request.getParameterValues("values");

                String requestParameters = "";
                for(int i=0; i<keys.length; i++){
                    switch (values[i]){
                        case Constants.PARAMETER_PROFILE: {
                            values[i] = String.valueOf(profileId);
                            break;
                        }
                        case Constants.PARAMETER_LOCATION: {
                            values[i] = String.valueOf(locationId);
                            break;
                        }
                    }
                    String var = String.format("%s=%s&",keys[i], values[i]);
                    requestParameters += var;
                }
                String actionValue = String.format("%s;%s;%s", requestType, requestUrl, requestParameters);
                action.setValue(actionValue);
                status = actionTable.addAction(action);
                break;
            }
        }

        if(status){
            response.getWriter().println("Success");
        }else {
            response.getWriter().println("Error");
        }
    }

    private String getOptions(Map<Integer, String> values){
//        String option = "<option value=\"%d\">%s</option>";
        String option = "<li value=\"%d\"><a href=\"#\">%s</a></li>";
        String optionSet = "";
        Iterator iterator = values.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry pair = (Map.Entry)iterator.next();
            optionSet += String.format(option, pair.getKey(), pair.getValue());
        }
        return optionSet;
    }
}
