package io.perfana.afterburner.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object SecureDelay {

  val call = exec(http("simple_delay")
            .get("/secured-delay?duration=200")
            .header("perfana-request-name", "simple_delay")
            .header("perfana-test-run-id", "${testRunId}")
            .basicAuth("pipo","test123")
            .check(status.is(200)))
        
}