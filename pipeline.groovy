pipeline {
    agent any

    // 1. Define the tools required for this pipeline.
    // These must be configured in Jenkins under "Manage Jenkins" -> "Global Tool Configuration".
    tools {
        maven 'Maven_3.8.5' // <-- Replace with the name of your Maven tool configuration
        jdk 'JDK_11'        // <-- Replace with the name of your JDK tool configuration
    }

    stages {
        stage('Get Code') {
            steps {
                git changelog: false, poll: false, url: 'https://github.com/ToubaSlam/Library_RESTAssured.git', branch: 'main'
            }
        }
        
        // NOTE: You still need a stage here to start your application
        // so it is available at http://localhost:3000 for the tests.
        // For example: stage('Start Application') { ... }
        
        stage('Build & Test') {
            steps {
                // 2. Use 'bat' for Windows agents. The 'mvn' command will use the tool defined above.
                bat 'mvn clean verify -DbaseURL="http://localhost:3000/"'
            }
        }
    }

    // 3. Use a 'post' block to run actions after stages complete.
    post {
        // 'always' ensures this runs even if the build fails, so you can see test failure reports.
        always {
            // 4. Use the Allure Plugin to generate and archive the report.
            allure includeProperties: false, jdk: 'JDK_11', report: 'target/allure-report', results: [[path: 'target/allure-results']]
        }
    }
}