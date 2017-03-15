package org.wso2.beaconManager.servlet.action;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.wso2.beaconManager.database.ActionTable;
import org.wso2.beaconManager.database.impl.ActionTableImpl;
import org.wso2.beaconManager.util.Action;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by wso2123 on 11/11/16.
 */
@WebServlet(name = "ImageUploadHandler")
public class ImageUploadHandler extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean status = false;
        Action action = new Action();

        String uploadDirectory = null;
        String os = System.getProperty("os.name");
        if(os.contains("Linux")){
            String user = System.getProperty("user.name");
            uploadDirectory = "/home/" + user + "/BeaconManager/images";
        }else if (os.contains("Windows")){
            uploadDirectory = "C:\\\\" + "BeaconManager\\images";
        }

        if(uploadDirectory!=null){
            File file = new File(uploadDirectory);
            if(!file.exists()){
                file.mkdirs();
            }

            String uploadPath = null;

            //Process only the encoding is multipart content
            if(ServletFileUpload.isMultipartContent(request)){
                try{
                    List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                    for(FileItem item: multiparts){
                        if(!item.isFormField()){
                            String name = new File(item.getName()).getName();
                            uploadPath = uploadDirectory + File.separator + name;
                            item.write(new File(uploadPath));
                        }else{
                            String name = item.getFieldName();
                            String value = item.getString();

                            switch (name){
                                case "profileHidden":{
                                    action.setProfileId(Integer.parseInt(value));
                                    break;
                                }
                                case "locationHidden": {
                                    action.setLocationId(Integer.parseInt(value));
                                    break;
                                }
                                case "typeHidden": {
                                    action.setType(value);
                                }
                            }
                        }
                    }
                } catch (FileUploadException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                action.setValue(uploadPath);
                ActionTable actionTable = new ActionTableImpl();
                status = actionTable.addAction(action);
            }
        }

        if(status){
            response.getWriter().println("Success");
        }else {
            response.getWriter().println("Error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
