package com.beltram.sleuthgreenwichreproducer

import brave.Tracer
import brave.context.slf4j.MDCScopeDecorator
import brave.propagation.CurrentTraceContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.config.EnableWebFlux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@SpringBootApplication(scanBasePackages = ["com.beltram"])
@EnableWebFluxSecurity
class TestApplication {
	
	@Configuration
	class SleuthConfiguration {
		
		@Bean
		fun mdcScopeDecorator(): CurrentTraceContext.ScopeDecorator = MDCScopeDecorator.create()
	}
	
	@RestController
	@RequestMapping("/spans")
	class TestController(private val tracer: Tracer) {
		
		@GetMapping
		fun currentSpan(): Mono<String> = tracer.currentSpan()?.toString()?.toMono() ?: IllegalStateException("Ooops").toMono()
	}
}
