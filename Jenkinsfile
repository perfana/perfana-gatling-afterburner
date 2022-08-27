


pipeline {

   agent any

    parameters {
        string(name: 'system_under_test', defaultValue: 'StarScream', description: 'Name used as System Under Test in Perfana')
        string(name: 'gatlingRepo', defaultValue: 'https://github.com/perfana/perfana-gatling-afterburner.git', description: 'Gatling git repository')
        choice(name: 'workload', choices: ['test-type-load', 'test-type-stress', 'test-type-slow-backend'], description: 'Workload profile to use in your Gatling script')
        string(name: 'annotations', defaultValue: '', description: 'Add annotations to the test run, these will be displayed in Perfana')
        string(name: 'targetDomain', defaultValue: 'star-scream-fe-afterburner', description: 'Target domain')
        string(name: 'targetPort', defaultValue: '8080', description: 'Target port')
        string(name: 'targetProtocol', defaultValue: 'http', description: 'Target protocol')

    }

    stages {

        stage('Checkout') {

            steps {

                script {

                    git url: params.gatlingRepo, branch: params.system_under_test

                }

            }

        }

        stage('Run performance test') {

            steps {

                script {

                    def perfanaUrl = "my.perfana"
                    def influxDbPassword = env.INFLUXDB_PASSWORD
                    def testRunId = env.JOB_NAME + "-" + env.BUILD_NUMBER
                    def version = "2.0." + env.BUILD_NUMBER
                    def buildUrl = env.BUILD_URL
                    def perfanaApiKey = env.PERFANA_API_KEY

                    // ** NOTE: This 'M3' maven tool must be configured
                    // **       in the global configuration.

                    def mvnHome = tool 'M3'

                    withKubeConfig( clusterName: 'acme', contextName: 'acme', credentialsId: 'kubeconfig-acme', namespace: 'acme') {

                        sh """
                           ${mvnHome}/bin/mvn clean verify -U -Ptest-env-demo,${params.workload},assert-results -Dsut-config=star-scream -DtestRunId=${testRunId} -DbuildResultsUrl=${buildUrl} -Dversion=${version} -DsystemUnderTest=${system_under_test} -Dannotations="${params.annotations}" -DapiKey=${perfanaApiKey} -DtargetDomain=${targetDomain} -DtargetPort=${targetPort} -DtargetProtocol=${targetProtocol} -DinfluxDbUrl=https://influxdb/write?db=jmeter -DinfluxDbUser=admin -DinfluxDbPassword=${influxDbPassword}
                        """
                    }
                }
            }
        }
    }

}
