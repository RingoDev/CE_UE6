package com.ringodev.factory;

import com.ringodev.factory.data.Configuration;
import com.ringodev.factory.data.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidatorService {

    static List<String> handlebarTypes = List.of("Flatbarlenker", "Rennradlenker", "Bullhornlenker");
    static List<String> handlebarMaterials = List.of("Aluminium", "Stahl", "Kunststoff");
    static List<String> handlebarGearshifts = List.of("Kettenschaltung", "Nabenschaltung", "Tretlagerschaltung");
    static List<String> handleTypes = List.of("Ledergriff", "Schaumstoffgriff", "Kunststoffgriff");


    RestrictionRepository restrictionRepository;

    @Autowired
    ValidatorService(RestrictionRepository restrictionRepository) {
        this.restrictionRepository = restrictionRepository;
    }


    List<String> getPossibleHandlebarTypes() {
        return handlebarTypes;
    }

    List<String> getPossibleMaterials(String handlebarType) {
        List<String> result = new ArrayList<>(3);
        for (String material : handlebarMaterials) {
            Configuration config = new Configuration();
            config.setHandlebarType(handlebarType);
            config.setHandlebarMaterial(material);
            if (validatePartialConfiguration(config)) result.add(material);
        }
        return result;
    }

    List<String> getPossibleGearshifts(String handlebarType, String handlebarMaterial) {
        List<String> result = new ArrayList<>(3);
        for (String gearshift : handlebarGearshifts) {
            Configuration config = new Configuration();
            config.setHandlebarType(handlebarType);
            config.setHandlebarMaterial(handlebarMaterial);
            config.setHandlebarGearshift(gearshift);
            if (validatePartialConfiguration(config)) result.add(gearshift);
        }
        return result;
    }

    List<String> getPossibleHandles(String handlebarType, String handlebarMaterial, String handlebarGearshift) {
        List<String> result = new ArrayList<>(3);
        for (String handle : handleTypes) {
            Configuration config = new Configuration();
            config.setHandlebarType(handlebarType);
            config.setHandlebarMaterial(handlebarMaterial);
            config.setHandlebarGearshift(handlebarGearshift);
            config.setHandlebarMaterial(handle);
            if (validatePartialConfiguration(config)) result.add(handle);
        }
        return result;
    }


    // checks partial configuration against all constraints in the DB
    public boolean validatePartialConfiguration(Configuration configuration) {
        for (Restriction restriction : restrictionRepository.findAll())
            if (!restriction.validate(configuration)) return false;
        return true;
    }
}
