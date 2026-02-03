package com.tesco.service;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
