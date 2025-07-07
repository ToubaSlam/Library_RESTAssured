pipeline {
    agent any

    tools {
        maven 'Maven_3.8.5'
        jdk 'JDK_11'
    }

    stages {
        stage('Get Code') {
            steps {
                git changelog: false, poll: false, url: 'https://github.com/ToubaSlam/Library_RESTAssured.git', branch: 'master'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean verify -DbaseURL="http://localhost:3000/"'
            }
        }

        stage('Generate Allure Report') {
            steps {
                bat 'allure generate target/allure-results -o target/allure-report --clean'
            }
        }
    }

    post {
        always {
            allure includeProperties: false, jdk: 'JDK_11', results: [[path: 'target/allure-results']]
        }
    }
}
