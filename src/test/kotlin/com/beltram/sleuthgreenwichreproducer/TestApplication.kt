package com.beltram.sleuthgreenwichreproducer

import brave.Tracer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@SpringBootApplication(scanBasePackages = ["com.beltram"])
@EnableWebFluxSecurity
class TestApplication {
	
	@RestController
	@RequestMapping("/spans")
	class TestController(private val tracer: Tracer) {
		
		@GetMapping
		fun currentSpan(): Mono<String> = tracer.currentSpan()?.toString()?.toMono() ?: IllegalStateException("Ooops").toMono()
	}
}
