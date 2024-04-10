package me.sigom.demoreactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDto {
    private String aggregateId;
    private String eventType;
    private String aggregateType;
    private long version;
}
