package com.ringodev.factory.data;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private String handlebarType;
    private String handlebarMaterial;
    private String handlebarGearshift;
    private String handleMaterial;


    public Configuration() {

    }

    public List<String> getAll() {
        List<String> list = new ArrayList<>(4);
        if (handlebarType != null) list.add(handlebarType);
        if (handlebarMaterial != null) list.add(handlebarMaterial);
        if (handlebarGearshift != null) list.add(handlebarGearshift);
        if (handleMaterial != null) list.add(handleMaterial);
        return list;
    }

    public String getHandlebarType() {
        return handlebarType;
    }

    public void setHandlebarType(String handlebarType) {
        this.handlebarType = handlebarType;
    }

    public String getHandlebarMaterial() {
        return handlebarMaterial;
    }

    public void setHandlebarMaterial(String handlebarMaterial) {
        this.handlebarMaterial = handlebarMaterial;
    }

    public String getHandlebarGearshift() {
        return handlebarGearshift;
    }

    public void setHandlebarGearshift(String handlebarGearshift) {
        this.handlebarGearshift = handlebarGearshift;
    }

    public String getHandleMaterial() {
        return handleMaterial;
    }

    public void setHandleMaterial(String handleMaterial) {
        this.handleMaterial = handleMaterial;
    }
}
