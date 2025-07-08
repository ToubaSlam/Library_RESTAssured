pipeline {
    agent any

    tools {
        maven 'Maven_3.8.5'
        jdk 'JDK_11'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/ToubaSlam/Library_RESTAssured.git', branch: 'master'
            }
        }

        stage('Build and Test') {
            steps {
                // Capture test failures but don't break the build
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat 'mvn clean verify -DbaseURL="http://localhost:3000/"'
                }
            }
        }


        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/allure-report/**', allowEmptyArchive: true
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            echo "Build finished. Check Allure report and JUnit results."
        }
    }
}
