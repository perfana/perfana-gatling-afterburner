package io.perfana.afterburner.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object RemoteDelay {

  val call = exec(http("remote_call_delayed")
    .get("/remote/call?path=delay")
    .check(status.is(200)))

}
