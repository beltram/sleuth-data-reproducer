package com.beltram.sleuthgreenwichreproducer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
public class SpanTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void findSimpleSpan() {
        webTestClient.get()
                .uri("/spans/simple")
                .exchange()
                .expectStatus().isOk()
                .expectBody(SpanWrapper.class)
                .consumeWith(e -> assertThat(e.getResponseBody().getSpans()).hasSize(1).allMatch(s -> !s.isBlank()));
    }

    @Test
    void findManySpans() {
        webTestClient.get()
                .uri("/spans/chain")
                .exchange()
                .expectStatus().isOk()
                .expectBody(SpanWrapper.class)
                .consumeWith(e -> assertThat(e.getResponseBody().getSpans()).hasSize(2).allMatch(s -> !s.isBlank()));
    }

    @Test
    void findManySpansWithMongo() {
        webTestClient.get()
                .uri("/spans/chain-with-mongo")
                .exchange()
                .expectStatus().isOk()
                .expectBody(SpanWrapper.class)
                .consumeWith(e -> assertThat(e.getResponseBody().getSpans()).hasSize(2).allMatch(s -> !s.isBlank()));
    }
}

