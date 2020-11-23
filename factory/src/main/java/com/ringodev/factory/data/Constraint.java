package com.ringodev.factory.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// part c1 can not be combined with c2
@Entity
public class Constraint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String c1;
    String c2;

    public Constraint(String c1,String c2){
        this.c1 = c1;
        this.c2 = c2;
    }

    public Constraint(){

    }

    public boolean validate(Configuration config) {
        return !config.getAll().contains(c1) || !config.getAll().contains(c2);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }
}
