package com.example.demo.service.impl;

import com.example.demo.service.UUIDGenerator;
import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Service;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import java.util.UUID;

/**
 * @author balineni
 * {@code @date} 4/4/2023
 */
@Service("uuidGeneratorImpl")
public class UUIDGeneratorImpl implements UUIDGenerator {

    private final TimeBasedGenerator generator;

    public UUIDGeneratorImpl() {
        EthernetAddress nic = EthernetAddress.fromInterface();
        generator = Generators.timeBasedGenerator(nic);
    }

    @Override
    public UUID generateUUID() {
        return generator.generate();
    }
}
