package android.saied.com.backend.di

import android.saied.com.backend.EnvironmentPropertiesReader
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.koin.dsl.module.module

val jsonModule = module {
    single {
        createObjectMapper()
    }

    single {
        EnvironmentPropertiesReader(get())
    }
}

private fun createObjectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())