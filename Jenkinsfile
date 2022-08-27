


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
        booleanParam(name: 'kubernetes', defaultValue: false, description: 'Run in Kubernetes')

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

                    def perfanaUrl = "demo.perfana.cloud"
                    def influxDbPassword = env.INFLUXDB_PASSWORD
                    def testRunId = env.JOB_NAME + "-" + env.BUILD_NUMBER
                    def version = "2.0." + env.BUILD_NUMBER
                    def buildUrl = env.BUILD_URL
                    def kubernetes = (params.kubernetes == true) ? "-Pkubernetes" : ""
                    def perfanaApiKey = env.PERFANA_API_KEY

                    // ** NOTE: This 'M3' maven tool must be configured
                    // **       in the global configuration.

                    def mvnHome = tool 'M3'

                    withKubeConfig(caCertificate: 'LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUJkakNDQVIyZ0F3SUJBZ0lCQURBS0JnZ3Foa2pPUFFRREFqQWpNU0V3SHdZRFZRUUREQmhyTTNNdGMyVnkKZG1WeUxXTmhRREUyTlRZMk1qTTBOekF3SGhjTk1qSXdOak13TWpFeE1URXdXaGNOTXpJd05qSTNNakV4TVRFdwpXakFqTVNFd0h3WURWUVFEREJock0zTXRjMlZ5ZG1WeUxXTmhRREUyTlRZMk1qTTBOekF3V1RBVEJnY3Foa2pPClBRSUJCZ2dxaGtqT1BRTUJCd05DQUFST3BqNG1CVzhwdmw0M2VrNllOOFczWitHU3lCZEUvSm9SMS8xd1Z6TkYKdHRubDBKZW5kQ0VHRGt0bjUva0pHS0ROVENob3dMSWNKOUxPV0JrK2d1ZVFvMEl3UURBT0JnTlZIUThCQWY4RQpCQU1DQXFRd0R3WURWUjBUQVFIL0JBVXdBd0VCL3pBZEJnTlZIUTRFRmdRVXdESmdpZGxQQmwrUElHZ2l4NWlWClFPUVlTdmd3Q2dZSUtvWkl6ajBFQXdJRFJ3QXdSQUlnQkRPVEUzekk1eUhOSENBaEZvaHVNdzFYVy93bStzVngKSUNWamZFUnEydkFDSUF2TFh4Z25HKzdsR1VJUnVFMnU3ZjFyaU5yNCt5UENLbnc1OVFkbXlXYjcKLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQo=', clusterName: 'acme', contextName: 'acme', credentialsId: 'kubeconfig-acme', namespace: 'acme', serverUrl: 'https://167.233.11.238:6443') {

                        sh """
                           ${mvnHome}/bin/mvn clean verify -U -Ptest-env-demo,${params.workload},assert-results -Dsut-config=star-scream -DtestRunId=${testRunId} -DbuildResultsUrl=${buildUrl} -Dversion=${version} -DsystemUnderTest=${system_under_test} -Dannotations="${params.annotations}" -DapiKey=${perfanaApiKey} -DtargetDomain=${targetDomain} -DtargetPort=${targetPort} -DtargetProtocol=${targetProtocol} -DinfluxDbUrl=https://influxdb.${perfanaUrl}/write?db=jmeter -DinfluxDbUser=admin -DinfluxDbPassword=${influxDbPassword}
                        """
                    }
                }
            }
        }
    }

}
