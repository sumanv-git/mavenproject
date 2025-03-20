package com.tesco.service;

import java.util.UUID;

public class IdGenerator {

    String generateId(){
        return UUID.randomUUID().toString();
    }
}
