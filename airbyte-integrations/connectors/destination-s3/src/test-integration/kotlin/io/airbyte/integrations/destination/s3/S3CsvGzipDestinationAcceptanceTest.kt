/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */
package io.airbyte.integrations.destination.s3

import com.fasterxml.jackson.databind.JsonNode
import io.airbyte.cdk.integrations.destination.s3.S3BaseCsvGzipDestinationAcceptanceTest

class S3CsvGzipDestinationAcceptanceTest : S3BaseCsvGzipDestinationAcceptanceTest() {
    override val baseConfigJson: JsonNode
        get() = S3DestinationTestUtils.baseConfigJsonFilePath
}
