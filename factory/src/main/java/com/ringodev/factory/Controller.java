package com.ringodev.factory;

import com.ringodev.factory.data.Configuration;
import client.RunClient;
import com.ringodev.factory.data.OrderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shared.Order;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

@RestController
@RequestMapping("api")
public class Controller {

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    OrderRepository orderRepository;
    ValidatorService validator;

    @Autowired
    Controller(ValidatorService validator, OrderRepository orderRepository) {
        this.validator = validator;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/handlebarType")
    public ResponseEntity<List<String>> getHandlebarType() {
        return new ResponseEntity<>(validator.getPossibleHandlebarTypes(), HttpStatus.OK);
    }

    @GetMapping("/handlebarMaterial")
    public ResponseEntity<List<String>> getHandlebarMaterial(@RequestParam String handlebarType) {
        if (handlebarType == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(validator.getPossibleMaterials(handlebarType), HttpStatus.OK);
    }

    @GetMapping("/handlebarGearshift")
    public ResponseEntity<List<String>> getHandlebarGearshift(@RequestParam String handlebarType, @RequestParam String handlebarMaterial) {
        if (handlebarType == null || handlebarMaterial == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(validator.getPossibleGearshifts(handlebarType, handlebarMaterial), HttpStatus.OK);
    }

    @GetMapping("/handleType")
    public ResponseEntity<List<String>> getHandleType(@RequestParam String handlebarType, @RequestParam String handlebarMaterial, @RequestParam String handlebarGearshift) {
        if (handlebarType == null || handlebarMaterial == null || handlebarGearshift == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(validator.getPossibleHandles(handlebarType, handlebarMaterial, handlebarGearshift), HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<Object> postConfiguration(@RequestBody Configuration config) throws RemoteException {

        if (validator.validateFullConfiguration(config)) {
            OrderImpl myOrder = new OrderImpl(config.getHandlebarType(), config.getHandlebarMaterial(), config.getHandlebarGearshift(), config.getHandleType());
            // to set id automatically
            orderRepository.save(myOrder);

            logger.info(myOrder.toOrder().toString());

            RunClient.sendOrderToFibu(myOrder.toOrder());

            // an lieferanten senden

            return new ResponseEntity<>(myOrder.toOrder(),HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
