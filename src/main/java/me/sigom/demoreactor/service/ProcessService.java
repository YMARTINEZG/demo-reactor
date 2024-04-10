package me.sigom.demoreactor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sigom.demoreactor.model.Event;
import me.sigom.demoreactor.model.RequestData;
import me.sigom.demoreactor.model.Snapshot;
import me.sigom.demoreactor.model.TransmissionStatus;
import me.sigom.demoreactor.repository.EventRepository;
import me.sigom.demoreactor.repository.SnapshotRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class ProcessService implements UseCaseProcess{
    private final SnapshotRepository snapshotRepository;
    private final EventRepository eventRepository;
    private final String soapServiceUrl;

    public ProcessService(SnapshotRepository snapshotRepository, EventRepository eventRepository, @Value("${web.service.client.url}")String soapServiceUrl) {
        this.snapshotRepository = snapshotRepository;
        this.eventRepository = eventRepository;
        this.soapServiceUrl = soapServiceUrl;
    }

    @Override
    public Mono<TransmissionStatus> sendMessage(RequestData request) throws IOException {
        return WebClient.create()
                .post()
                .uri(soapServiceUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request.getMessage()), String.class)
                .retrieve()
                .bodyToMono(TransmissionStatus.class)
                .doOnSuccess(this::save)
                .doOnError(Exception.class, ( Exception error ) -> {
                    System.out.println( "error : "+ error );
                });
    }

    @Override
    public Mono<Snapshot> findByAggregateId(String id) {
        return snapshotRepository.findByAggregateId(id);
    }

    @Override
    public Mono<Event> findById(String id) {
        return eventRepository.findById(UUID.fromString(id));
    }

    @Override
    public Flux<Event> findAllAggregates() {
        return eventRepository.findAll();
    }

    @Override
    public Mono<String> save(TransmissionStatus dto) {
        Event event = new Event(dto.getAggregateId(), dto.getEventType(), dto.getAggregateType(), dto.getVersion());
        Mono<Event> mEvent = createEvent(event);
        Mono<Snapshot> mSnapshot = createSnapshot(event);
        mEvent.subscribe(s -> log.info("COMPLETED SAVE"), (e) -> System.out.println("ITEM NOT FOUND " + e.getMessage()));
        mSnapshot.subscribe();
        return Mono.just(event.getAggregateId());
    }
    public Mono<Event> createEvent(Event event) {
        return eventRepository.save(event);
    }

    private Mono<Snapshot> createSnapshot(Event event) {
        log.info("PERSISTING SNAPSHOT >>>>>");
        Snapshot snapshot = new Snapshot(event.getAggregateId(), event.getAggregateType(), event.getVersion());
        Mono<Boolean> existSnapshot = findByAggregateId(snapshot.getAggregateId()).hasElement();
        return existSnapshot.flatMap(exist -> exist ? Mono.error(new Exception("Snapshot already in use"))
                : snapshotRepository.save(snapshot)
        );
    }
}
