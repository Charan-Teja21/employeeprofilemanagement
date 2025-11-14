pipeline {
    agent any

    environment {
        // Docker image name
        KUBECONFIG = "/Users/makacharanteja/.kube/config"
        DOCKER_IMAGE = "employeeprofilemanagement_image"

        // PostgreSQL database URL (change if needed)
        DB_URL = "jdbc:postgresql://host.docker.internal:5432/epms_db"
        DB_USERNAME = "postgres"
        DB_PASSWORD = "postgres"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Charan-Teja21/employeeprofilemanagement'
            }
        }

        stage('Build') {
            steps {
                sh '''
                    echo "🚀 Building Docker image..."
                    docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} -f Dockerfile .
                '''
            }
        }

        stage('Run Container') {
            steps {
                script {
                    sh '''
                        echo "🧹 Cleaning up any existing container..."

                        # Stop and remove existing container if it exists
                        if [ "$(docker ps -aq -f name=employeeprofilemanagement)" ]; then
                            echo "🛑 Stopping existing container..."
                            docker stop employeeprofilemanagement || true

                            echo "🗑️ Removing existing container..."
                            docker rm -f employeeprofilemanagement || true
                        fi

                        echo "🐳 Running new Docker container..."
                        docker run -d \
                            --name employeeprofilemanagement \
                            -e SPRING_DATASOURCE_URL=${DB_URL} \
                            -e SPRING_DATASOURCE_USERNAME=${DB_USERNAME} \
                            -e SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD} \
                            -p 8200:8200 \
                            ${DOCKER_IMAGE}:${BUILD_NUMBER}
                    '''
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    echo "📦 Updating Docker image in Kubernetes manifest..."

                    # macOS-compatible sed fix
                    sed -i '' "s|image: .*|image: ${DOCKER_IMAGE}:${BUILD_NUMBER}|g" deployment.yaml

                    echo "📁 Applying Namespace..."
                    kubectl apply -f namespace.yaml

                    echo "🚀 Deploying to Kubernetes..."
                    kubectl apply -n employeemanagementsystem -f deployment.yaml
                    kubectl apply -n employeemanagementsystem -f service.yaml

                    echo "⏳ Waiting for rollout to finish..."
                    kubectl rollout status deployment/employeemanagementsystem-deployment -n employeemanagementsystem

                    echo "✅ Kubernetes deployment completed!"
                '''
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