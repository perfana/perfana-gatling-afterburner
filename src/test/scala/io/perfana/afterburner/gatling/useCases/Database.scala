package io.perfana.afterburner.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.collection.immutable.Map
import scala.concurrent.duration._

object Database {

  val call = exec(http("database_call")
            .get("/remote/call-many?count=3&path=/db/employee/name?firstName=${FIRST_NAME}")
            .header("perfana-request-name", "database_call")
            .header("perfana-test-run-id", "${testRunId}")
            .check(status.is(200)))
        
}