package me.sigom.demoreactor.service;

import me.sigom.demoreactor.model.Event;
import me.sigom.demoreactor.model.RequestData;
import me.sigom.demoreactor.model.Snapshot;
import me.sigom.demoreactor.model.TransmissionStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface UseCaseProcess {
    Mono<TransmissionStatus> sendMessage(RequestData request) throws IOException;
    Mono<Snapshot> findByAggregateId(String aggregateId);

    Mono<Event> findById(String id);

    Flux<Event> findAllAggregates();

    Mono<TransmissionStatus> save(TransmissionStatus dto);


}
