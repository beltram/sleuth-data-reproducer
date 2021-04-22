package com.beltram.sleuthgreenwichreproducer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication(scanBasePackages = "com.beltram")
public class TestApplication {
}
