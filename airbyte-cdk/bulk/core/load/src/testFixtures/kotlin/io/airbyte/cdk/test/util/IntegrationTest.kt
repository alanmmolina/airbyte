package io.airbyte.cdk.test.util

import io.airbyte.cdk.Operation
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

@MicronautTest
class IntegrationTest {
    @Test
    @Property(name = Operation.PROPERTY, value = "write")
    fun foo(op: Operation) {
        println(op)
    }
}
