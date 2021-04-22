package com.beltram.sleuthgreenwichreproducer;

import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.stream.Stream;

import static com.beltram.sleuthgreenwichreproducer.TracerHelper.spanId;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/spans")
public class TestController {

    private final Tracer tracer;
    private final PersonRepository repo;

    public TestController(Tracer tracer, PersonRepository repo) {
        this.tracer = tracer;
        this.repo = repo;
    }

    @GetMapping("simple")
    Mono<SpanWrapper> simple() {
        return spanId(tracer)
                .map(s -> new SpanWrapper().setSpans(Stream.of(s).collect(toList())));
    }

    @GetMapping("chain")
    Mono<SpanWrapper> chain() {
        SpanWrapper result = new SpanWrapper().setSpans(new ArrayList<>());
        return spanId(tracer)
                .map(result::add)
                .flatMap(it -> spanId(tracer))
                .map(result::add)
                .map(it -> result);
    }

    @GetMapping("chain-with-mongo")
    Mono<SpanWrapper> chainMongo() {
        SpanWrapper result = new SpanWrapper().setSpans(new ArrayList<>());
        return spanId(tracer)
                .map(result::add)
                .flatMap(s -> repo.insert(new Person(s)))
                .flatMap(p -> spanId(tracer)) // <-- spanId is empty here
                .map(result::add)
                .map(it -> result);
    }
}
