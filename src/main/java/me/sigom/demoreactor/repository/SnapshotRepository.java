package me.sigom.demoreactor.repository;

import me.sigom.demoreactor.model.Snapshot;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SnapshotRepository extends ReactiveCrudRepository<Snapshot, UUID> {
    Mono<Snapshot> findByAggregateId(String id);
}
