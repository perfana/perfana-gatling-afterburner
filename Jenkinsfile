


pipeline {

   agent any

    parameters {
        string(name: 'perfana_url', defaultValue: 'http://perfana-auth-proxy.monitoring/', description: 'Perfana url')
        string(name: 'system_under_test', defaultValue: 'OptimusPrime', description: 'Name used as System Under Test in Perfana')
        string(name: 'gatlingRepo', defaultValue: 'https://github.com/perfana/perfana-gatling-afterburner.git', description: 'Gatling git repository')
        choice(name: 'workload', choices: ['test-type-load', 'test-type-stress', 'test-type-slow-backend', 'test-type-cpu'], description: 'Workload profile to use in your Gatling script')
        string(name: 'annotations', defaultValue: '', description: 'Add annotations to the test run, these will be displayed in Perfana')
        string(name: 'targetBaseUrl', defaultValue: 'http://optimus-prime-fe-afterburner:8080', description: 'Target Url')
        string(name: 'apiKey', defaultValue: '', description: 'Perfana API key, will override secret if provided')
        string(name: 'influxUrl', defaultValue: 'http://perfana-auth-proxy.monitoring/influxdb', description: 'InfluxDb URL')
        string(name: 'influxUser', defaultValue: '', description: 'InfluxDb User')      
        string(name: 'influxPassword', defaultValue: '', description: 'InfluxDb Password')      
        booleanParam(name: 'kubernetes', defaultValue: false, description: 'Run in Kubernetes')

    }

    stages {

        stage('Checkout') {

            steps {

                script {

                    git url: params.gatlingRepo, branch: 'OptimusPrime-acme-2'

                }

            }

        }

        stage('Run performance test') {

            steps {

                script {

                    def testRunId = env.JOB_NAME + "-" + env.BUILD_NUMBER
                    def version = "2.0." + env.BUILD_NUMBER
                    def buildUrl = env.BUILD_URL
                    def kubernetes = (params.kubernetes == true) ? "-Pkubernetes" : ""


                    // ** NOTE: This 'M3' maven tool must be configured
                    // **       in the global configuration.

                    def mvnHome = tool 'M3'

                    withCredentials([string(credentialsId: 'perfanaApiKey', variable: 'TOKEN'), string(credentialsId: 'elasticPassword', variable: 'ESPWD'), string(credentialsId: 'influxUser', variable: 'INFLUX_USER'), string(credentialsId: 'influxPassword', variable: 'INFLUX_PWD')]) {

                       if(params.apiKey != "") {
                          
                          sh """
                              ${mvnHome}/bin/mvn clean install -U events-gatling:test -Ptest-env-demo,${params.workload},assert-results -DtestRunId=${testRunId} -DbuildResultsUrl=${buildUrl} -Dversion=${version} -DsystemUnderTest=${system_under_test} -Dannotations="${params.annotations}" -DelasticPassword=$ESPWD -DapiKey=${params.apiKey} -DtargetBaseUrl=${targetBaseUrl} -DinfluxUrl=${params.influxUrl} -DinfluxUser="$INFLUX_USER" -DinfluxPassword="$INFLUX_PWD"  -DperfanaUrl=${params.perfana_url} ${kubernetes}
                           """
                          
                       } else {    
                        
                           sh """
                              ${mvnHome}/bin/mvn clean install -U events-gatling:test -Ptest-env-demo,${params.workload},assert-results -DtestRunId=${testRunId} -DbuildResultsUrl=${buildUrl} -Dversion=${version} -DsystemUnderTest=${system_under_test} -Dannotations="${params.annotations}" -DelasticPassword=$ESPWD -DapiKey=$TOKEN -DtargetBaseUrl=${targetBaseUrl} -DinfluxUrl=${params.influxUrl} -DinfluxUser="$INFLUX_USER" -DinfluxPassword="$INFLUX_PWD" -DperfanaUrl=${params.perfana_url} ${kubernetes}
                           """
                       }   
                    }
                }
            }
        }
    }

}
