package io.airbyte.cdk.test.util

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.airbyte.protocol.models.v0.AirbyteMessage
import io.airbyte.protocol.models.v0.AirbyteRecordMessage
import io.airbyte.protocol.models.v0.AirbyteRecordMessageMeta
import io.airbyte.protocol.models.v0.AirbyteRecordMessageMetaChange
import java.time.Instant

/**
 * A record that we intend to insert to the destination. Basically just a clean version of
 * [io.airbyte.protocol.models.AirbyteRecordMessage].
 */
data class InputRecord(
    val extractedAt: Instant,
    val data: JsonNode,
    val recordChanges: List<AirbyteRecordMessageMetaChange>?,
) {
    /**
     * Convenience constructors, so callers don't need to manually parse everything
     *
     * @param data The data blob as a serialized JSON object
     */
    constructor(
        extractedAt: Long,
        data: String,
        // TODO maybe easier to accept a string here and json deserialize to object
        recordChanges: List<AirbyteRecordMessageMetaChange>?,
    ): this(
        Instant.ofEpochMilli(extractedAt),
        ObjectMapper().readTree(data),
        recordChanges,
    )

    fun toAirbyteMessage(streamName: String, streamNamespace: String?): AirbyteMessage =
        AirbyteMessage()
            .withType(AirbyteMessage.Type.RECORD)
            .withRecord(
                AirbyteRecordMessage()
                    .withStream(streamName)
                    .withNamespace(streamNamespace)
                    .withEmittedAt(extractedAt.toEpochMilli())
                    .withData(data)
                    .withMeta(AirbyteRecordMessageMeta().withChanges(recordChanges))
            )
}
