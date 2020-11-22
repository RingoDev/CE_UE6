package com.ringodev.factory;

import com.ringodev.factory.data.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    static List<String> handlebarTypes = List.of("Flatbarlenker", "Rennradlenker", "Bullhornlenker");
    static List<String> handlebarMaterials = List.of("Aluminium", "Stahl", "Kunststoff");
    static List<String> handlebarGearshifts = List.of("Kettenschaltung", "Nabenschaltung", "Tretlagerschaltung");
    static List<String> handleTypes = List.of("Ledergriff", "Schaumstoffgriff", "Kunststoffgriff");

    static List<String> getPossibleHandlebarTypes() {
        return handlebarTypes;
    }

    static List<String> getPossibleMaterials(String handlebarType) {
        List<String> result = new ArrayList<>(3);
        for (String material : handlebarMaterials) {
            Configuration config = new Configuration();
            config.setHandlebarType(handlebarType);
            config.setHandlebarMaterial(material);
            if (validateConfiguration(config)) result.add(material);
        }
        return result;
    }

    static List<String> getPossibleGearshifts(String handlebarType, String handlebarMaterial) {
        List<String> result = new ArrayList<>(3);
        for (String gearshift : handlebarGearshifts) {
            Configuration config = new Configuration();
            config.setHandlebarType(handlebarType);
            config.setHandlebarMaterial(handlebarMaterial);
            config.setHandlebarGearshift(gearshift);
            if (validateConfiguration(config)) result.add(gearshift);
        }
        return result;
    }

    static List<String> getPossibleHandles(String handlebarType, String handlebarMaterial, String handlebarGearshift) {
        List<String> result = new ArrayList<>(3);
        for (String handle : handleTypes) {
            Configuration config = new Configuration();
            config.setHandlebarType(handlebarType);
            config.setHandlebarMaterial(handlebarMaterial);
            config.setHandlebarGearshift(handlebarGearshift);
            config.setHandlebarMaterial(handle);
            if (validateConfiguration(config)) result.add(handle);
        }
        return result;
    }


    static public boolean validateConfiguration(Configuration configuration) {
        if (configuration.getHandlebarMaterial().equals("Stahl")) {
            if (configuration.getHandlebarType().equals("Faltbarlenker")) {
                return false;
// Der Faltbarlenker kann nur aus Aluminium oder Kunststoff bestehen
            } else if (configuration.getHandlebarType().equals("Rennradlenker")) {
                return false;
// Der Rennradlenker kann nur aus Aluminium oder Kunststoff bestehen
            }

            if (!configuration.getHandlebarGearshift().equals("Kettenschaltung")) {
                return false;
// Das Material Stahl kann nur kombiniert werden mit der Kettenschaltung
            }
        }

        //Bedingung 4
        if (configuration.getHandleMaterial().equals("Kunststoffgriff")) {
            if (!configuration.getHandleMaterial().equals("Kunststoff")) {
                return false;
// Der Kunststoffgriff kann nur mit dem Material Kunststoff kombiniert werden
            }
        }
        if (configuration.getHandleMaterial().equals("Ledergriff")) {
            if (!configuration.getHandlebarType().equals("Rennradlenker")) {
                return false;
// Der Ledergriff kann nur beim Rennradlenker verwendet werden
            }
        }
        return true;
    }


}
