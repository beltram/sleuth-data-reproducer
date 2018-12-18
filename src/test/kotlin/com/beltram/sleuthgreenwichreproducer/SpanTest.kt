package com.beltram.sleuthgreenwichreproducer

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@AutoConfigureWebTestClient
@WithMockUser(username="beltram")
internal class SpanTest(@Autowired val webTestClient: WebTestClient) {

	@Test
    fun `find spand 1`() {
        webTestClient.get()
			.uri("/spans")
			.exchange()
			.expectStatus().isOk
			.expectBody<String>()
			.returnResult().apply { assertThat(responseBody!!).isNotBlank() }
    }
	
	@Test
    fun `find spand 2`() {
        webTestClient.get()
			.uri("/spans")
			.exchange()
			.expectStatus().isOk
			.expectBody<String>()
			.returnResult().apply { assertThat(responseBody!!).isNotBlank() }
    }
	
	@Test
	fun `find spand 3`() {
		webTestClient.get()
				.uri("/spans")
				.exchange()
				.expectStatus().isOk
				.expectBody<String>()
				.returnResult().apply { assertThat(responseBody!!).isNotBlank() }
	}
}
