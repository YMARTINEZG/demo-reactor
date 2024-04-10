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
public class Snapshot {
    private UUID snapshotId;
    private String aggregateId;
    private String aggregateType;
    private byte[] data;
    private byte[] metadata;
    private long version;
    private ZonedDateTime timestamp;

    public Snapshot(String aggregateId, String aggregateType, long version){
        this.snapshotId = UUID.randomUUID();
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.version = version;
        this.timestamp = ZonedDateTime.now();
    }
    @Override
    public String toString() {
        return "Snapshot{" +
                "id=" + snapshotId +
                ", aggregateId='" + aggregateId + '\'' +
                ", aggregateType='" + aggregateType + '\'' +
                ", version=" + version +
                ", timeStamp=" + timestamp +
                '}';
    }
}
