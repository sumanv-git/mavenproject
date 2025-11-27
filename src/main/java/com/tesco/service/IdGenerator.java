package com.tesco.service;

import java.util.UUID;

public class IdGenerator {

    public String generateId(){
        return UUID.randomUUID().toString();
    }
}
