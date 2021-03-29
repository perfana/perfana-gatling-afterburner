package io.perfana.afterburner.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object FlakyCall {

  val call = exec(http("flaky_call")
            .post("/flaky?maxRandomDelay=2000&flakiness=25")
            .header("perfana-request-name", "flaky_call")
            .header("perfana-test-run-id", "${testRunId}")
            .check(status.is(200))
        )

}