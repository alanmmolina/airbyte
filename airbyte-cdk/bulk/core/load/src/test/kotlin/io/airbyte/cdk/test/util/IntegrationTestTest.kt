package io.airbyte.cdk.test.util

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import javax.inject.Inject
import javax.inject.Singleton
import org.junit.jupiter.api.Test

@MicronautTest
class IntegrationTestTest() {
    @Inject
    @Test
    @Property(name = "testtest", value = "string")
    fun foo(thingDoer: ThingDoer) {
        println(thingDoer.conf)
    }
}

interface Conf

@Singleton
@Requires(property = "testtest", value = "string")
class StringConf(): Conf

@Singleton
@Requires(property = "testtest", value = "int")
class IntConf(): Conf

@Singleton
class ThingDoer(val conf: Conf)
