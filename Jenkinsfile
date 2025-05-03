pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "mprem799/questionsgenerator"
        DOCKER_TAG = "${env.BRANCH_NAME}-${env.BUILD_ID}"  // Unique tag using branch and build ID
    }

    stages {
        stage('Clone Repo') {
            steps {
                script {
                    checkout scm  // Automatically clones the repo for current branch
                }
            }
        }

        stage('Check Commit Message Format') {
            steps {
                script {
                    def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()
                    echo "Latest Commit Message: ${commitMessage}"

                    if (!commitMessage.matches("^(feat|fix|chore|docs|style|refactor|perf|test):\\d{4}-.+")) {
                        error("❌ Commit message does not follow required format: feat:0000-description")
                    } else {
                        echo "✅ Commit message format is valid"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                echo '🏗️ Building your project...'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "🐳 Building Docker image ${DOCKER_IMAGE}:${DOCKER_TAG}..."
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Test Docker Image') {
            steps {
                script {
                    echo "🧪 Testing Docker image..."
                    // Replace with actual test logic later, for now just check if container runs
                    sh "docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG} echo '✅ Container ran successfully'"
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        echo "🔐 Logging in and pushing Docker image to Docker Hub..."
                        sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    }
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                script {
                    echo "🚀 Deploying Docker container..."
                    // Stop and remove existing container if it exists
                    sh "docker rm -f ${DOCKER_IMAGE}-${DOCKER_TAG} || true"
                    // Run new container
                    sh "docker run -d --name ${DOCKER_IMAGE}-${DOCKER_TAG} -p 8080:8080 ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        stage('Clean Up Docker Images') {
            steps {
                script {
                    echo "🧹 Cleaning up Docker image..."
                    sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} || true"
                }
            }
        }
    }

    post {
        always {
            echo "♻️ Post-cleanup steps..."
            sh "docker container prune -f"
            sh "docker image prune -f"
        }
    }
}
