package com.ringodev.factory;

import com.ringodev.factory.data.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class Controller {

    ValidatorService validator;
    @Autowired
    Controller(ValidatorService validator){
        this.validator = validator;
    }

    @GetMapping("/handlebarType")
    public ResponseEntity<List<String>> getHandlebarType() {
        return new ResponseEntity<>(validator.getPossibleHandlebarTypes(), HttpStatus.OK);
    }

    @GetMapping("/handlebarMaterial")
    public ResponseEntity<List<String>> getHandlebarMaterial(@RequestParam String handlebarType) {
        return new ResponseEntity<>(validator.getPossibleMaterials(handlebarType), HttpStatus.OK);
    }

    @GetMapping("/handlebarGearshift")
    public ResponseEntity<List<String>> getHandlebarGearshift(@RequestParam String handlebarType, @RequestParam String handlebarMaterial) {
        return new ResponseEntity<>(validator.getPossibleGearshifts(handlebarType, handlebarMaterial), HttpStatus.OK);
    }

    @GetMapping("/handleMaterial")
    public ResponseEntity<List<String>> getHandleType(@RequestParam String handlebarType, @RequestParam String handlebarMaterial, @RequestParam String handlebarGearshift) {
        return new ResponseEntity<>(validator.getPossibleHandles(handlebarType, handlebarMaterial, handlebarGearshift), HttpStatus.OK);
    }

    @PostMapping("/configuration")
    public ResponseEntity<Object> postConfiguration(@RequestBody Configuration config) {

        // kontrollieren ob vollständig und valide
        // an fibu senden
        // an lieferanten senden

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
