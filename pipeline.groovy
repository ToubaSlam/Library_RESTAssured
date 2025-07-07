pipeline {
    agent any

    tools {
        maven 'Maven_3.8.5' // Ensure this matches your configured Maven tool name
        jdk 'JDK_11'        // Ensure this matches your configured JDK tool name
    }

    environment {
        BASE_URL = "http://localhost:3000"
    }

    stages {
        stage('Get Code') {
            steps {
                git changelog: false, poll: false, url: 'https://github.com/ToubaSlam/Library_RESTAssured.git', branch: 'master'
            }
        }

        stage('Build & Test') {
            steps {
                // Ignore failures to ensure the build continues to reporting
                bat 'mvn clean verify -DbaseURL="%BASE_URL%" -Dsurefire.testFailureIgnore=true'
            }
        }

        stage('Generate Allure Report') {
            steps {
                bat 'allure generate target\\allure-results -o target\\allure-report --clean'
            }
        }
    }

    post {
        always {
            allure includeProperties: false, jdk: 'JDK_11', results: [[path: 'target/allure-results']]
        }
    }
}
