package com.ringodev.factory;

import com.ringodev.factory.data.Configuration;
import com.ringodev.factory.data.Constraint;
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


    ConstraintRepository constraintRepository;

    @Autowired
    ValidatorService(ConstraintRepository constraintRepository) {
        this.constraintRepository = constraintRepository;
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
            if (validateConfiguration(config)) result.add(material);
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
            if (validateConfiguration(config)) result.add(gearshift);
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
            if (validateConfiguration(config)) result.add(handle);
        }
        return result;
    }


    // checks configuration against all constraints in the DB
    public boolean validateConfiguration(Configuration configuration) {
        for (Constraint constraint : constraintRepository.findAll())
            if (!constraint.validate(configuration)) return false;
        return true;
    }
}
