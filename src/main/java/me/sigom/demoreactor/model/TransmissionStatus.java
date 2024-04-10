package me.sigom.demoreactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class TransmissionStatus {
    private String status;
    private String msg;
    private String aggregateId;
    private String eventType;
    private String aggregateType;
    private long version;
}
