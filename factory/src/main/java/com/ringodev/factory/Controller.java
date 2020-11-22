package com.ringodev.factory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class Controller {


    @PostMapping("/options")
    public ResponseEntity<Object> getOptions(@RequestBody Configuration currentConfig){

        return new ResponseEntity<>(new Configuration(),HttpStatus.OK);
    }

    static class Configuration{

    }
}
