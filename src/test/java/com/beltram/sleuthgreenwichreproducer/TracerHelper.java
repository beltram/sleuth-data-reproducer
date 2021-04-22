package com.beltram.sleuthgreenwichreproducer;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.TraceContext;
import org.springframework.cloud.sleuth.Tracer;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class TracerHelper {

    public static Mono<String> spanId(Tracer tracer) {
        return Mono.just(Optional.ofNullable(tracer.currentSpan())
                .map(Span::context)
                .map(TraceContext::spanId)
                .orElse(""));
    }
}
