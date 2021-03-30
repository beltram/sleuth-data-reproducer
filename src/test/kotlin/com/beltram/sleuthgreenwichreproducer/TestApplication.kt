package com.beltram.sleuthgreenwichreproducer

import brave.Tracer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@EnableReactiveMongoRepositories
@SpringBootApplication(scanBasePackages = ["com.beltram"])
class TestApplication {

    @RestController
    @RequestMapping("/spans")
    class TestController(private val tracer: Tracer, private val repo: PersonRepository) {

        @GetMapping("simple")
        fun simple(): Mono<SpanWrapper> = tracer.spanId()
                .map { SpanWrapper(mutableListOf(it)) }

        @GetMapping("chain")
        fun chain(): Mono<SpanWrapper> {
            val result = SpanWrapper()
            return tracer.spanId()
                    .map { result.add(it) }
                    .flatMap { tracer.spanId() }
                    .map { result.add(it) }
                    .map { result }
        }

        @GetMapping("chain-with-mongo")
        fun chainMongo(): Mono<SpanWrapper> {
            val result = SpanWrapper()
            return tracer.spanId()
                    .map { result.add(it) }
                    .flatMap { repo.insert(Person(it)) }
                    .flatMap { tracer.spanId() } // <-- spanId is empty here
                    .map { result.add(it) }
                    .map { result }
        }
    }

    data class SpanWrapper(val spans: MutableList<String> = mutableListOf()) {
        fun add(span: String): String = span.also { spans.add(it) }
    }

    data class Person(val span: String)
}

fun Tracer.spanId() = currentSpan()?.context()?.spanIdString().orEmpty().toMono()
