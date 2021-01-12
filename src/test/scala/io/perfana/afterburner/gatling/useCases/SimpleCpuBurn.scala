package io.perfana.afterburner.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.collection.immutable.Map
import scala.concurrent.duration._

object SimpleCpuBurn {

  val call = exec(http("simple_cpu_burn_instant")
            .get("/cpu/magic-identity-check?matrixSize=133")
            .header("perfana-request-name", "simple_cpu_burn_instant")
            .header("perfana-test-run-id", "${testRunId}")
            .check(status.is(200)))
        
}