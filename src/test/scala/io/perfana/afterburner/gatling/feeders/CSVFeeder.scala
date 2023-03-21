package io.perfana.afterburner.gatling.feeders

import io.gatling.core.Predef._

/**
 * Created by x077411 on 12/12/2014.
 */
object CSVFeeder {

  val firstName = csv("data/firstNames.csv").random

}