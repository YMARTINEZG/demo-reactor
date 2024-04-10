package me.sigom.demoreactor.controller;

import me.sigom.demoreactor.model.TransmissionStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MockWebService {
    @PostMapping("/test_soap/endpoint1")
    private TransmissionStatus getMessage(@RequestBody String msg) {
        return new TransmissionStatus("200", msg, UUID.randomUUID().toString(), "BANK_ACCOUNT", msg, 1L);
    }
}
