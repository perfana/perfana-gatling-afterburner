package io.perfana.afterburner.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.collection.immutable.Map
import scala.concurrent.duration._

object SimpleDelay {

  val call = exec(http("simple_delay_immediate")
            .get("/delay?duration=555")
            .header("perfana-request-name", "simple_delay")
            .header("perfana-test-run-id", "${testRunId}")
            .check(status.is(200)))
        
}