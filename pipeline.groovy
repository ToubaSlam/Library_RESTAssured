pipeline {
    agent any

    tools {
        maven 'Maven_3.8.5'
        jdk 'JDK_11'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git changelog: false, poll: false, url: 'https://github.com/ToubaSlam/Library_RESTAssured.git', branch: 'master'
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
            junit '**/target/surefire-reports/*.xml' // Publish test results to Jenkins UI
            archiveArtifacts artifacts: 'target/allure-report/**', fingerprint: true
        }
        failure {
            echo '⚠️ Tests failed! Please check the logs above or the Allure report for more details.'
        }
        success {
            echo '✅ All tests passed successfully!'
        }
    }
}
