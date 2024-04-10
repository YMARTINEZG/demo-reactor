package me.sigom.demoreactor.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    private UUID id;
    private String aggregateId;
    private String eventType;
    private String aggregateType;
    private long version;
    private byte[] data;
    private byte[] metadata;
    private ZonedDateTime timestamp;

    public Event(String aggregateId, String eventType, String aggregateType, long version) {
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.aggregateType = aggregateType;
        this.version = version;
        this.timestamp = ZonedDateTime.now();
    }
}
