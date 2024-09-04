package io.airbyte.cdk.test.util

import com.fasterxml.jackson.databind.JsonNode
import io.airbyte.cdk.Operation
import io.airbyte.protocol.models.v0.AirbyteMessage
import io.airbyte.protocol.models.v0.ConfiguredAirbyteCatalog

interface DestinationProcess {
    fun sendMessage(message: AirbyteMessage)

    /**
     * Return all messages from the destination. When the destination exits, the last message
     * MUST be `null`.
     *
     * (mediocre interface, just for demo purposes)
     */
    fun readMessages(): List<AirbyteMessage?>

    fun waitUntilDone()
}

class NonDockerizedDestination(
    command: String,
    config: JsonNode,
    catalog: ConfiguredAirbyteCatalog,
    // some other param to get whatever code we're actually running,
    // i.e. equivalent to io.airbyte.integrations.base.destination.Destination
    operation: Operation,
): DestinationProcess {
    init {
        // invoke whatever CDK stuff exists to run a destination connector
        // but use some reasonable interface instead of stdin/stdout
    }

    override fun sendMessage(message: AirbyteMessage) {
        TODO("Not yet implemented")
    }

    override fun readMessages(): List<AirbyteMessage> {
        TODO("Not yet implemented")
    }

    override fun waitUntilDone() {
        // send a "stdin closed" signal
        TODO("Not yet implemented")
    }
}

class DockerizedDestination(
    command: String,
    config: JsonNode,
    catalog: ConfiguredAirbyteCatalog,
): DestinationProcess {
    init {
        // launch a docker container...
    }

    override fun sendMessage(message: AirbyteMessage) {
        // push a message to the docker process' stdin
        TODO("Not yet implemented")
    }

    override fun readMessages(): List<AirbyteMessage> {
        // read everything from the process' stdout
        TODO("Not yet implemented")
    }

    override fun waitUntilDone() {
        // close stdin, wait until process exits
        TODO("Not yet implemented")
    }
}
