


pipeline {

   agent any

    parameters {
        string(name: 'system_under_test', defaultValue: 'OptimusPrime', description: 'Name used as System Under Test in Perfana')
        string(name: 'gatlingRepo', defaultValue: 'https://github.com/perfana/perfana-gatling-afterburner.git', description: 'Gatling git repository')
        choice(name: 'workload', choices: ['test-type-stress', 'test-type-load', 'test-type-slow-backend'], description: 'Workload profile to use in your Gatling script')
        string(name: 'annotations', defaultValue: '', description: 'Add annotations to the test run, these will be displayed in Perfana')
        string(name: 'targetBaseUrl', defaultValue: 'http://optimus-prime-fe:8080', description: 'Target Url')
        booleanParam(name: 'kubernetes', defaultValue: false, description: 'Run in Kubernetes')

    }

    stages {

        stage('Checkout') {

            steps {

                script {

                    git url: params.gatlingRepo, branch: "temp-demo-pp"

                }

            }

        }

        stage('Run performance test') {

            steps {

                script {

                    //def testRunId = env.JOB_NAME + "-" + env.BUILD_NUMBER
                    def version = "1.0." + env.BUILD_NUMBER
                    def buildUrl = env.BUILD_URL
                    def kubernetes = (params.kubernetes == true) ? "-Pkubernetes" : ""


                    // ** NOTE: This 'M3' maven tool must be configured
                    // **       in the global configuration.

                    def mvnHome = tool 'M3'

                    withCredentials([string(credentialsId: 'perfanaApiKey', variable: 'TOKEN')]) {

                        def post = new URL("http://perfana:3000/api/init").openConnection();
                        def message = '{ "testEnvironment": "acc", "systemUnderTest": "OptimusPrime", "workload": "stressTest" }'
                        post.setRequestMethod("POST")
                        post.setDoOutput(true)
                        post.setRequestProperty("Content-Type", "application/json")
                        post.setRequestProperty("Authorization", "Bearer " + TOKEN)
                        post.getOutputStream().write(message.getBytes("UTF-8"));
                        def postRC = post.getResponseCode();
                        println(postRC);
                        def testRunId
                        if (postRC.equals(200)) {
                            def json = post.getInputStream().getText();
                            testRunId = groovy.json.JsonSlurper().parseText(json).testRunId
                            println(testRunId)
                        }
                        else {
                            json = post.getErrorStream().getText();
                            println(json);
                        }

                        sh """
                           ${mvnHome}/bin/mvn clean install -U -X events-gatling:test -Ptest-env-demo,${params.workload},assert-results -DtestRunId=${testRunId} -DbuildResultsUrl=${buildUrl} -Dversion=${version} -DsystemUnderTest=${system_under_test} -Dannotations="${params.annotations}" -DapiKey=$TOKEN -DtargetBaseUrl=${targetBaseUrl} ${kubernetes}
                        """
                    }
                }
            }

        }
    }

}
