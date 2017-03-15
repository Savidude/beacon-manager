package org.wso2.bleagent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.wso2.bleagent.constants.Constants;
import org.wso2.bleagent.transport.Client;
import org.wso2.bleagent.transport.ManagerClient;
import org.wso2.bleagent.util.BeaconProperties;
import org.wso2.bleagent.util.EddystoneProperties;
import org.wso2.bleagent.util.LocalRegistry;
import org.wso2.bleagent.util.dto.AgentUtil;
import org.wso2.bleagent.util.dto.deviceRegistrationUtils.Action;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity implements OnDataSendToActivity, BeaconConsumer, RangeNotifier {
    private static final int REQUEST_PERMISSION = 10;
    private static final long MAX_CONN_TIME = 5000;

    private ProgressBar spinner;
    private TextView statusText;
    private WebView webView;

    private BeaconManager beaconManager;
    private List<BeaconProperties> recentBeacons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestReadPhoneStatePermissions();
        recentBeacons = new ArrayList<>();

        spinner = (ProgressBar) findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);

        statusText = (TextView) findViewById(R.id.statusText);
        statusText.setText("Connecting to server...");
        statusText.setVisibility(View.VISIBLE);

        webView = (WebView) findViewById(R.id.webView);
        this.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                boolean status = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                    status = true;
                }
                return status;
            }
        });

        serverConnect();
    }

    @Override
    public void onBeaconConnection(Action action) {
        String type = action.getType();

        switch (type){
            case Constants.ACTION_IMAGE:{
                webView.loadUrl(ManagerClient.requestImage(action.getValue()));
                break;
            }
            case Constants.ACTION_URL:{
                webView.loadUrl(action.getValue());
                break;
            }
            case Constants.ACTION_ENDPOINT:{
                String[] endpointAttributes = action.getValue().split(";");
                ManagerClient.sendRequestToManager(endpointAttributes);
                break;
            }
        }

        if(action!=null){
            spinner.setVisibility(View.INVISIBLE);
            statusText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(this);
        Region region = new Region("all-beacons-region", null, null, null);
        try{
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
        double closestDistance = 1000;
        int closestIndex = 0;

        int i = 0;
        for(Beacon beacon: collection){
            double distance = beacon.getDistance();
            if (distance < closestDistance){
                closestIndex = i;
                closestDistance = distance;
            }
            i++;
        }

        int j = 0;
        for (Beacon beacon: collection){
            if(j==closestIndex){
                String namespace = beacon.getId1().toString();
                String instance = beacon.getId2().toString();

                EddystoneProperties properties = new EddystoneProperties();
                properties.setNamespace(namespace);
                properties.setInstance(instance);
                properties.setConnectedTime(System.currentTimeMillis());

                if(checkBeaconHistory(properties)){
                    recentBeacons.add(properties);
                    Client.beaconConnect(this, properties);
                    break;
                }
            }
            j++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.unbind(this);
    }

    private void serverConnect(){
        try {
            Properties properties = new Properties();
            InputStream inputStream = getBaseContext().getAssets().open("deviceConfig.properties");
            properties.load(inputStream);

            String url = properties.getProperty("https-ep");
            String profile = properties.getProperty("profile-id");

            //TODO obtain following values from .properties file
            String username = "admin";
            String password = "admin";

            boolean registered = Client.register(url, username, password);
            if (registered){
                LocalRegistry localRegistry = LocalRegistry.getInstance();
                localRegistry.setUrl(url);
                localRegistry.setUsername(username);
                localRegistry.setPassword(password);
                localRegistry.setProfile(profile);

                startEddystoneMonitoring();
                statusText.setText("Scanning for nearby items...");
            }else {
                statusText.setText("Could not connect to server");
                spinner.setVisibility(View.INVISIBLE);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private void startEddystoneMonitoring(){
        beaconManager = BeaconManager.getInstanceForApplication(getApplicationContext());
        //Detecting Eddystone_UUID frame
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19"));
        beaconManager.bind(this);
    }

    private void requestReadPhoneStatePermissions(){
        String deviceId = null;
        try {
            deviceId = AgentUtil.generateDeviceId(getBaseContext(), getContentResolver());
        }catch (SecurityException e){
            int permissionCheck = -1;
            do{
                permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
                if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION);
                    permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
                }
            } while (permissionCheck != PackageManager.PERMISSION_GRANTED);

            deviceId = AgentUtil.generateDeviceId(getBaseContext(), getContentResolver());
        }
        LocalRegistry.getInstance().setDeviceId(deviceId);
    }

    private boolean checkBeaconHistory(BeaconProperties properties){
        boolean status = true;
        ArrayList<Integer> oldBeacons = new ArrayList<>();

        for(int i=0; i<recentBeacons.size(); i++){
            //If in the most recently detected beacon and it is equal to the currently detected beacon
            if((i==recentBeacons.size()-1) && properties.equals(recentBeacons.get(i))){
                status = false;
            }else{
                //Selecting beacons connected more than 5 seconds ago
                long timeDifference = properties.getConnectedTime() - recentBeacons.get(i).getConnectedTime();
                if(timeDifference > MAX_CONN_TIME){
                    oldBeacons.add(i);
                }else if (properties.equals(recentBeacons.get(i))){
                    status = false;
                    break;
                }
            }
        }

        //Hardcoded removal of beacon H (testing purposes only)
        //TODO: Remove this code
        EddystoneProperties eddystoneProperties = new EddystoneProperties();
        eddystoneProperties.setNamespace("0xedd1ebeac04e5defa017");
        eddystoneProperties.setInstance("0x2a8379c199c0");
        if(properties.equals(eddystoneProperties)){
            status = false;
        }

        //Removing beacons connected more than 5 seconds ago
        for(int j=oldBeacons.size()-1; j>0; j--){
            int recentIndex = oldBeacons.get(j);
            recentBeacons.remove(recentIndex);
        }

        return status;
    }
}
