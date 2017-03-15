package org.wso2.beaconManager.transport;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wso2123 on 11/10/16.
 */
public class EndpointRequestExecutor {

    public static boolean requestEndpoint(String requestType, String requestUrl, Map<String, String> params){
        boolean status = false;

        try {
            String requestData = "";

            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry pair = (Map.Entry)iterator.next();

                if(requestData.length()>0){
                    requestData += "&" + generateEncodedAttribute(pair.getKey().toString(), pair.getValue().toString());
                }else {
                    requestData += generateEncodedAttribute(pair.getKey().toString(), pair.getValue().toString());
                }
            }

            URL url = new URL(requestUrl + "?" + requestData);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(requestType.toUpperCase());

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(requestData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            status = true;

            bufferedReader.close();
            inputStream.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return status;
    }

    private static String generateEncodedAttribute(String key, String value) throws UnsupportedEncodingException {
        String encodedAttribute = URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
        return encodedAttribute;
    }
}
