package me.sigom.demoreactor.repository;

import me.sigom.demoreactor.model.Event;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface EventRepository extends ReactiveCrudRepository<Event, UUID> {
}
