package me.sigom.demoreactor.controller;

import lombok.extern.slf4j.Slf4j;
import me.sigom.demoreactor.model.*;
import me.sigom.demoreactor.service.ProcessService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping(value="/api")
public class ProcessController {

    private final ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }


    @PostMapping({ "/test", "/" })
    public Mono<ResponseEntity<?>> processPayload(@RequestBody RequestData request) throws IOException {

        Mono<TransmissionStatus> e = processService.sendMessage(request);
        return e.map(t ->
        {
            if (t.getStatus().equals("200")) {
                return new ResponseEntity<>(t.getAggregateId(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(t.getAggregateId(), HttpStatus.BAD_REQUEST);
            }
        });
    }

    @GetMapping("/event/{id}")
    public void findById(@PathVariable("id") String id) {

        Mono<?> snapEvent = processService.findByAggregateId(id)
                .doOnNext(i -> System.out.println("On next: " + i.getAggregateId()))
                .doOnSuccess(i -> log.info("value of i = " + i))
                .doOnError(ex -> System.out.println("On error: " + ex.getMessage()));

        snapEvent.subscribe(s -> log.info("COMPLETED"), (e) -> System.out.println("ITEM NOT FOUND"));

    }

    @PostMapping("/event/create")
    public Mono<String> createEvent(@RequestBody EventDto dto){
           Event event = new Event(dto.getAggregateId(), dto.getEventType(), dto.getAggregateType(), dto.getVersion());
//           return processService.save(event);
           return Mono.just(event.getAggregateId());
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<Event>> listAll(){
        return ResponseEntity.ok(processService.findAllAggregates());
    }
}
