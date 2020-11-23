package com.ringodev.supplier.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("api")
public class Controller {

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/offer")
    public ResponseEntity<Object> getOffer(@RequestBody Offer offer) {
        logger.info("Got an offer");
        return new ResponseEntity<>(new Response(new Random().nextInt(100), new Date()), HttpStatus.OK);
    }

    static class Response {
        Response() {
        }

        Response(int price, Date date) {
            this.price = price;
            this.date = date.getTime();
        }

        int price;
        long date;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }
    }

    static class Offer {

    }
}
