package io.perfana.afterburner.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object GroupedCalls {

  val call = group("Group of calls") {
    exec(http("remote_call_async")
      .get("/remote/call-many?count=3&path=delay")
      .header("perfana-request-name", "remote_call_async")
      .header("perfana-test-run-id", "${testRunId}")
      .check(status.is(200)))
    .exec(http("remote_call_delayed")
      .get("/remote/call?path=remote/call?path=delay")
      .header("perfana-request-name", "remote_call_delayed")
      .header("perfana-test-run-id", "${testRunId}")
      .check(status.is(200)))
    .exec(http("simple_cpu_burn")
      .get("/cpu/magic-identity-check?matrixSize=133")
      .header("perfana-request-name", "simple_cpu_burn")
      .header("perfana-test-run-id", "${testRunId}")
      .check(status.is(200)))
  }
}

