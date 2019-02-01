package com.beltram.sleuthgreenwichreproducer

import com.beltram.sleuthgreenwichreproducer.TestApplication.SpanWrapper
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@AutoConfigureWebTestClient
internal class SpanTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun `find simple span`() {
        webTestClient.get()
                .uri("/spans/simple")
                .exchange()
                .expectStatus().isOk
                .expectBody<SpanWrapper>()
                .returnResult().apply {
                    val spans = responseBody!!.spans
                    assertThat(spans).hasSize(1).allMatch { it.isNotBlank() }
                }
    }

    @Test
    fun `find many spans`() {
        webTestClient.get()
                .uri("/spans/chain")
                .exchange()
                .expectStatus().isOk
                .expectBody<SpanWrapper>()
                .returnResult().apply {
                    val spans = responseBody!!.spans
                    assertThat(spans).hasSize(2).allMatch { it.isNotBlank() }
                }
    }

    @Test
    fun `find many spans with mongo`() {
        webTestClient.get()
                .uri("/spans/chain-with-mongo")
                .exchange()
                .expectStatus().isOk
                .expectBody<SpanWrapper>()
                .returnResult().apply {
                    val spans = responseBody!!.spans
                    assertThat(spans).hasSize(2).allMatch { it.isNotBlank() }
                }
    }
}
