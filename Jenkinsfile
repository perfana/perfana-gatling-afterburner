node("master") {

    def testRunId = env.JOB_NAME + "-" + env.BUILD_NUMBER
    def version = "1.0." + env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    // Mark the code checkout 'stage'....

    stage 'Checkout'

    // Get script from a GitHub repository
    git url: 'https://github.com/perfana/perfana-gatling-afterburner.git', branch: 'burn-after-wiremock'
    // Get the maven tool.
    // ** NOTE: This 'M3' maven tool must be configured
    // **       in the global configuration.
    def mvnHome = tool 'M3'


    stage 'Execute load test'

    // Run the test
    sh "${mvnHome}/bin/mvn clean install -U events-gatling:test -Ptest-env-acc,test-type-slow-backend,assert-results -DtestRunId=$testRunId -DbuildResultsUrl=$buildUrl -DapplicationRelease=$version "



}
