package org.wso2.beaconManager.util;

/**
 * Created by wso2123 on 11/29/16.
 */
public class EddystoneLocation {
    private int id;
    private String name;
    private String namespace;
    private String instance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
}
