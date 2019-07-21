package com.nsv.jsmbaba;

import org.apache.log4j.Logger;

public class Calculator {

    private static final Logger log = Logger.getLogger(Calculator.class);
    public int add(int a, int b){
        log.info("adding "+a+" +"+b);
        return a+b;
    }

    public void setPrice(int price){
        if(price<100){
            try {
                throw new Exception("Price is invalid");
            } catch (Exception e) {
                log.error("price is invalid"+price);
                log.error(e.getMessage());
            }
        }
    }
}
