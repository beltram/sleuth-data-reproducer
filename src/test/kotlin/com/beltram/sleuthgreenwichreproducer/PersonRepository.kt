package com.beltram.sleuthgreenwichreproducer

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PersonRepository : ReactiveMongoRepository<TestApplication.Person, ObjectId>