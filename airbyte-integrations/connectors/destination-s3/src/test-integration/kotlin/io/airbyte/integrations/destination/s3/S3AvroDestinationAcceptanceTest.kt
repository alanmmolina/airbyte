/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */
package io.airbyte.integrations.destination.s3

import com.fasterxml.jackson.databind.JsonNode
import io.airbyte.cdk.integrations.destination.s3.S3BaseAvroDestinationAcceptanceTest
import io.airbyte.cdk.integrations.standardtest.destination.comparator.TestDataComparator

class S3AvroDestinationAcceptanceTest : S3BaseAvroDestinationAcceptanceTest() {
    override fun getTestDataComparator(): TestDataComparator {
        return S3AvroParquetTestDataComparator()
    }

    override val baseConfigJson: JsonNode
        get() = S3DestinationTestUtils.baseConfigJsonFilePath
}
