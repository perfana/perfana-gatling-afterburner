docker run --rm  \
-e SIMULATION=io.perfana.afterburner.gatling.setup.OptimusPrime \
-e SCENARIO=acceptance \
-e TEST_RUN_ID=test-1 \
-e TEST_ENVIRONMENT=acme \
-e SYSTEM_UNDER_TEST=OptimusPrime \
-e TARGET_BASE_URL=http://localhost:8080 \
-e INITIAL_USERS_PER_SECOND=1 \
-e TARGET_USERS_PER_SECOND=2 \
-e RAMPUP_TIME_IN_SECONDS=60 \
-e CONSTANT_LOAD_TIME_IN_SECONDS=300 \
-e ELASTIC_PASSWORD=bla \
-e EMPLOYEE_DB_PASSWORD=bla \
-e DEBUG=false \
-e USE_PROXY=false \
-e INFLUX_DB_HOST=influx \
-e INFLUX_DB_URL=http://bla \
-e INFLUX_DB_USER=foo \
-e INFLUX_DB_PASSWORD=bar \
gatling-runner