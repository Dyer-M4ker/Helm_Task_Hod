pipeline {
    agent any

    tools {
        jdk 'Temurin-17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Gradle Build') {
            steps {
                bat '.\\gradlew.bat clean build'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'build/libs/calculator-cli.jar', fingerprint: true, onlyIfSuccessful: true
                junit 'build/test-results/test/*.xml'
                jacoco(
                        execPattern: 'build/jacoco/test.exec',
                        classPattern: 'build/classes/java/main',
                        sourcePattern: 'src/main/java',
                        changeBuildStatus: true
                )
            }
        }

        stage('Optional Docker Build') {
            when {
                expression { return fileExists('Dockerfile') }
            }
            steps {
                script {
                    try {
                        bat 'docker --version'
                        bat 'docker build -t calculator-cli .'
                    } catch (err) {
                        echo "Skipping Docker build: ${err}"
                    }
                }
            }
        }
    }
}
