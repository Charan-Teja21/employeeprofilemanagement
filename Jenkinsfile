pipeline {
    agent any

    environment {
        // Uncomment below if pushing to DockerHub
        // DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        DOCKER_IMAGE = "employeeprofilemanagement_image"

        // Database URL used when dockerization is done for the application
        DB_URL = "jdbc:postgresql://host.docker.internal:5432/epms_db"
    }

    // Various stages in the Pipeline Process:
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/somanadha/employeeprofilemanagement.git'
            }
        }

        stage('Build') {
            steps {
                // Build docker image using Dockerfile
                bat "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} -f Dockerfile ."
            }
        }

        stage('Run Container') {
            steps {
                script {
                    bat "docker run -e DB_URL=jdbc:postgresql://host.docker.internal:5432/epms_db -d --name employeeprofilemanagement -p 8200:8200 ${DOCKER_IMAGE}:${BUILD_NUMBER}"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Checkout, Build, Dockerize & Deploy completed successfully!"
        }
        failure {
            echo "❌ Build failed!"
        }
    }
}
