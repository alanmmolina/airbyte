/*
 * Copyright (c) 2024 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.cdk.test

import java.time.Instant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MaybeRecordDifferTest {
    @Test
    fun testBasicBehavior() {
        val differ =
            RecordDiffer(
                extractPrimaryKey = { listOf(it.data["id1"], it.data["id2"]) },
                extractCursor = { it.data["updated_at"] }
            )

        val diff =
            differ.diffRecords(
                expectedRecords =
                    listOf(
                        // Extra expected record
                        OutputRecord(
                            extractedAt = 1234,
                            generationId = 42,
                            mapOf(
                                "id1" to 1,
                                "id2" to 100,
                                "updated_at" to Instant.parse("1970-01-01T00:00:00Z"),
                                "name" to "alice",
                                "phone" to "1234"
                            ),
                            airbyteMeta = null
                        ),
                        // Matching records
                        OutputRecord(
                            extractedAt = 1234,
                            generationId = 42,
                            mapOf(
                                "id1" to 1,
                                "id2" to 100,
                                "updated_at" to Instant.parse("1970-01-01T00:00:01Z"),
                                "name" to "bob",
                            ),
                            airbyteMeta = null
                        ),
                        // Different records
                        OutputRecord(
                            extractedAt = 1234,
                            generationId = 42,
                            mapOf(
                                "id1" to 1,
                                "id2" to 100,
                                "updated_at" to Instant.parse("1970-01-01T00:00:02Z"),
                                "name" to "charlie",
                                "phone" to "1234",
                                "email" to "charlie@example.com"
                            ),
                            airbyteMeta = """{"sync_id": 12}""",
                        ),
                    ),
                actualRecords =
                    listOf(
                        // Matching records
                        OutputRecord(
                            extractedAt = 1234,
                            generationId = 42,
                            mapOf(
                                "id1" to 1,
                                "id2" to 100,
                                "updated_at" to Instant.parse("1970-01-01T00:00:01Z"),
                                "name" to "bob",
                            ),
                            airbyteMeta = null
                        ),
                        // Different records
                        OutputRecord(
                            extractedAt = 1234,
                            generationId = 41,
                            mapOf(
                                "id1" to 1,
                                "id2" to 100,
                                "updated_at" to Instant.parse("1970-01-01T00:00:02Z"),
                                "name" to "charlie",
                                "phone" to "5678",
                                "address" to "1234 charlie street"
                            ),
                            airbyteMeta = null
                        ),
                        // Extra actual record
                        OutputRecord(
                            extractedAt = 1234,
                            generationId = 42,
                            mapOf(
                                "id1" to 1,
                                "id2" to 100,
                                "updated_at" to Instant.parse("1970-01-01T00:00:03Z"),
                                "name" to "dana",
                            ),
                            airbyteMeta = null
                        ),
                    ),
            )

        Assertions.assertEquals(
            """
            Missing record (pk=[1, 100], cursor=1970-01-01T00:00:00Z): OutputRecord(rawId=null, extractedAt=1970-01-01T00:00:01.234Z, loadedAt=null, generationId=42, data={id1=1, id2=100, updated_at=1970-01-01T00:00:00Z, name=alice, phone=1234}, airbyteMeta=null)
            Incorrect record ((pk=[1, 100], cursor=1970-01-01T00:00:02Z):
              generationId: Expected 42, got 41
              airbyteMeta: Expected {"sync_id":12}, got null
              phone: Expected 1234, but was 5678
              email: Expected charlie@example.com, but was <unset>
              address: Expected <unset>, but was 1234 charlie street
            Unexpected record (pk=[1, 100], cursor=1970-01-01T00:00:03Z): OutputRecord(rawId=null, extractedAt=1970-01-01T00:00:01.234Z, loadedAt=null, generationId=42, data={id1=1, id2=100, updated_at=1970-01-01T00:00:03Z, name=dana}, airbyteMeta=null)
            """.trimIndent(),
            diff
        )
    }
}
