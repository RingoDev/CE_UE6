package com.ringodev.factory;

import com.ringodev.factory.data.Configuration;
import com.ringodev.factory.data.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ValidatorService {

    enum Part {
        handlebarType, handlebarMaterial, handlebarGearshift, handleType
    }

    static Map<Part, List<String>> types = Map.of(
            Part.handlebarType, List.of("Flatbarlenker", "Rennradlenker", "Bullhornlenker"),
            Part.handlebarMaterial, List.of("Aluminium", "Stahl", "Kunststoff"),
            Part.handlebarGearshift, List.of("Kettenschaltung", "Nabenschaltung", "Tretlagerschaltung"),
            Part.handleType, List.of("Ledergriff", "Schaumstoffgriff", "Kunststoffgriff"));

    RestrictionRepository restrictionRepository;

    @Autowired
    ValidatorService(RestrictionRepository restrictionRepository) {
        this.restrictionRepository = restrictionRepository;
    }

    List<String> getPossibleHandlebarTypes() {
        return types.get(Part.handlebarType);
    }

    List<String> getPossibleMaterials(String handlebarType) {
        List<String> result = new ArrayList<>(3);
        for (String material : types.get(Part.handlebarMaterial)) {
            Configuration config = new Configuration();
            config.setHandlebarType(handlebarType);
            config.setHandlebarMaterial(material);
            if (validatePartialConfiguration(config)) result.add(material);
        }
        return result;
    }

    List<String> getPossibleGearshifts(String handlebarType, String handlebarMaterial) {
        List<String> result = new ArrayList<>(3);
        for (String gearshift : types.get(Part.handlebarGearshift)) {
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
        for (String handle : types.get(Part.handleType)) {
            Configuration config = new Configuration();
            config.setHandlebarType(handlebarType);
            config.setHandlebarMaterial(handlebarMaterial);
            config.setHandlebarGearshift(handlebarGearshift);
            config.setHandleType(handle);
            if (validatePartialConfiguration(config)) result.add(handle);
        }
        return result;
    }

    // checks full Configuration
    public boolean validateFullConfiguration(Configuration configuration) {
        if (!types.get(Part.handlebarType).contains(configuration.getHandlebarType())) return false;
        if (!types.get(Part.handlebarMaterial).contains(configuration.getHandlebarMaterial())) return false;
        if (!types.get(Part.handlebarGearshift).contains(configuration.getHandlebarGearshift())) return false;
        if (!types.get(Part.handleType).contains(configuration.getHandleType())) return false;
        return validatePartialConfiguration(configuration);
    }

    // checks partial configuration against all constraints in the DB
    public boolean validatePartialConfiguration(Configuration configuration) {
        for (Restriction restriction : restrictionRepository.findAll())
            if (!restriction.validate(configuration)) {
                return false;
            }
        return true;
    }
}
