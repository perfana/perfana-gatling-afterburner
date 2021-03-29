package io.perfana.afterburner.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object FlakyCall {

  val call = exec(http("flaky_call")
            .get("/flaky?maxRandomDelay=500&flakiness=25")
            .header("perfana-request-name", "flaky_call")
            .header("perfana-test-run-id", "${testRunId}")
            .check(status.is(200))
        )

}