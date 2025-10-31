pipeline {
    agent any

    tools {
        jdk 'Temurin-17'
    }

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean Workspace') {
            steps {
                bat """
                if exist out rmdir /S /Q out
                """
            }
        }

        stage('Compile') {
            steps {
                bat """
                mkdir out\\classes
                javac -d out\\classes src\\main\\java\\com\\example\\calculator\\*.java
                """
            }
        }

        stage('Package JAR') {
            steps {
                bat """
                jar --create --file out\\calculator-cli.jar --main-class com.example.calculator.CalculatorCLI -C out\\classes .
                """
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'out/calculator-cli.jar', fingerprint: true, onlyIfSuccessful: true
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
