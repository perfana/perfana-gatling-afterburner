
#!/bin/sh
# ENV VARS:
# - REPORT_BUCKET: S3 bucket where to copy the simulation.log file to
# - SIMULATION: Full classpath of simulation file to run, e.g. nl.codecontrol.gatling.simulations.BasicSimulation

# start g2i
./g2i ./results -a $INFLUX_DB_URL -u $INFLUX_DB_USER -p $INFLUX_DB_PASSWORD -b gatling3 -t $TEST_ENVIRONMENT -y $SYSTEM_UNDER_TEST -d | awk '{print $2}'

# Run Gatling from jar
USER_ARGS=""
COMPILATION_CLASSPATH=`find -L ./target -maxdepth 1 -name "*.jar" -type f -exec printf :{} ';'`
JAVA_OPTS="-server -Xmx1G -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:+HeapDumpOnOutOfMemoryError -XX:MaxInlineLevel=20 -XX:MaxTrivialSize=12 -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Addresses=false ${JAVA_OPTS}"
java $JAVA_OPTS \
-Dscenario=$SCENARIO \
-DtestRunId=$TEST_RUN_ID \
-DtestEnvironment=$TEST_ENVIRONMENT \
-DsystemUnderTest=$SYSTEM_UNDER_TEST \
-DtargetBaseUrl=$TARGET_BASE_URL \
-DinitialUsersPerSecond=$INITIAL_USERS_PER_SECOND \
-DtargetUsersPerSecond=$TARGET_USERS_PER_SECOND \
-DrampupTimeInSeconds=$RAMPUP_TIME_IN_SECONDS \
-DconstantLoadTimeInSeconds=$CONSTANT_LOAD_TIME_IN_SECONDS \
-DelasticPassword=$ELASTIC_PASSWORD \
-DemployeeDbPassword=$EMPLOYEE_DB_PASSWORD \
-Ddebug=$DEBUG \
-DuseProxy=$USE_PROXY \
-Dgatling.data.graphite.rootPathPrefix=gatling2.$SYTEM_UNDER_TEST.$TEST_ENVIRONMENT \
-Dgatling.data.graphite.host=$INFLUX_DB_HOST \
$USER_ARGS -cp $COMPILATION_CLASSPATH io.gatling.app.Gatling -s $SIMULATION

