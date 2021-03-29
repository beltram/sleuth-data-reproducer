package com.beltram.sleuthgreenwichreproducer

import com.beltram.sleuthgreenwichreproducer.TestApplication.Person
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PersonRepository : ReactiveMongoRepository<Person, ObjectId>