


pipeline {

   agent any

    parameters {
        string(name: 'system_under_test', defaultValue: 'OptimusPrime', description: 'Name used as System Under Test in Perfana')
        string(name: 'gatlingRepo', defaultValue: 'https://github.com/perfana/perfana-gatling-afterburner.git', description: 'Gatling git repository')
        choice(name: 'workload', choices: ['test-type-load', 'test-type-stress', 'test-type-slow-backend'], description: 'Workload profile to use in your Gatling script')
        string(name: 'annotations', defaultValue: '', description: 'Add annotations to the test run, these will be displayed in Perfana')
        string(name: 'targetBaseUrl', defaultValue: 'http://optimus-prime-fe:8080', description: 'Target Url')
        booleanParam(name: 'kubernetes', defaultValue: false, description: 'Run in Kubernetes')

    }

    stages {

        stage('Checkout') {

            steps {

                script {

                    git url: params.gatlingRepo, branch: params.system_under_test + "-jdbc-feeder"

                }

            }

        }

        stage('Run performance test') {

            steps {

                script {

                    def testRunId = env.JOB_NAME + "-" + env.BUILD_NUMBER
                    def version = "1.0." + env.BUILD_NUMBER
                    def buildUrl = env.BUILD_URL
                    def kubernetes = (params.kubernetes == true) ? "-Pkubernetes" : ""


                    // ** NOTE: This 'M3' maven tool must be configured
                    // **       in the global configuration.

                    def mvnHome = tool 'M3'

                    sh """
                       ${mvnHome}/bin/mvn clean install -U events-gatling:test -Ptest-env-demo,${params.workload},assert-results -DtestRunId=${testRunId} -DbuildResultsUrl=${buildUrl} -Dversion=${version} -DsystemUnderTest=${system_under_test} -Dannotations="${params.annotations}" -DtargetBaseUrl=${targetBaseUrl} ${kubernetes}
                    """
                }
            }

        }
    }

}
