pipeline {
    agent any

    tools {
        maven 'Maven_3.8.5' // Ensure this matches Jenkins tool configuration
        jdk 'JDK_11'        // Ensure this matches Jenkins tool configuration
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/ToubaSlam/Library_RESTAssured.git', branch: 'master'
            }
        }

        stage('Run Tests') {
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
            archiveArtifacts artifacts: 'target/allure-report/**', allowEmptyArchive: true
        }
    }
}
