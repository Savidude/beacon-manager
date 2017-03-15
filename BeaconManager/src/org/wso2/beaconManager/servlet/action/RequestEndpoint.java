package org.wso2.beaconManager.servlet.action;

import org.wso2.beaconManager.transport.EndpointRequestExecutor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wso2123 on 11/10/16.
 */
@WebServlet(name = "/request_endpoint")
public class RequestEndpoint extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String url = request.getParameter("url");
        String params = request.getParameter("params");

        Map<String, String> paramsMap = new HashMap<>();
        String[] attributes = params.split("&");
        for(String attribute: attributes) {
            String[] pair = attribute.split("=");
            String key = pair[0];
            String value = pair[1];
            paramsMap.put(key, value);
        }

        EndpointRequestExecutor.requestEndpoint(type, url, paramsMap);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
