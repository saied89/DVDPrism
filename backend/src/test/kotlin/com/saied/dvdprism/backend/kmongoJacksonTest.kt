package com.saied.dvdprism.backend

import com.fasterxml.jackson.annotation.JsonProperty
import com.mongodb.client.MongoCollection
import org.junit.jupiter.api.Test
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import kotlin.test.assertEquals

data class Model(
    @JsonProperty("DVD")
    val dvd: String
)

internal class KmongoJacksonTest {

    private val testCollection: MongoCollection<Model> = KMongo.createClient().getDatabase("TEST").getCollection()

    @Test
    fun `model is saved correctly`() {
        val model = Model("")
        testCollection.insertOne(model)

        val res = testCollection.findOne()

        assertEquals(model, res)
    }
}