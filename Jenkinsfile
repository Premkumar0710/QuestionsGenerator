pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "mprem799/questionsgenerator"
        DOCKER_TAG = "${env.BRANCH_NAME}-${env.BUILD_ID}"  // Unique tag using branch and build ID
        NGROK_SCRIPT_PATH = "C:\\Prem\\Backend Projects\\QuestionsGenerator\\github-webhook.ps1"  // Path to ngrok script
    }

    stages {
        stage('Clone Repo') {
            steps {
                script {
                    checkout scm  // Automatically clones the repo for the current branch
                }
            }
        }

       stage('Check Commit Message Format') {
           steps {
               script {
                   // Safely retrieve the latest commit message line in Windows
                   def fullCommitLine = bat(
                       script: '''
                           @echo off
                           for /f "delims=" %%i in ('git log -1 --pretty=oneline') do echo %%i
                           ''',
                       returnStdout: true
                   ).trim()

                   echo "Commit Line: ${fullCommitLine}"

                   // Extract the commit message (everything after the first space)
                   def matcher = fullCommitLine =~ /^[0-9a-f]{7,40}\s+(.*)/
                   if (!matcher.matches()) {
                       error "❌ Failed to parse commit message"
                   }
                   def commitMessage = matcher[0][1].trim()

                   echo "Commit Message: ${commitMessage}"

                   // Regex pattern to validate message (e.g., "fix:1234 - corrected spacing issue")
                   def pattern = ~/^(feat|fix|chore|test):\d{4}-.*$/

                   if (!(commitMessage ==~ pattern)) {
                       error "❌ Invalid commit message format. Expected format: type:0000-message"
                   } else {
                       echo "✅ Commit message format is valid"
                   }
               }
           }
       }


        stage('Build') {
            steps {
                echo '🏗️ Building your project...'
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Start ngrok and Update GitHub Webhook') {
            steps {
                script {
                    echo '🔄 Starting ngrok and updating GitHub Webhook...'
                    // Run ngrok and update webhook with the new URL using PowerShell script
                    bat 'start /B ngrok http 8080'  // Start ngrok in the background
                    sleep(time: 15, unit: 'SECONDS') // Wait for ngrok to establish the tunnel

                    // Run PowerShell script to update GitHub webhook with the ngrok URL
                    bat "powershell.exe -ExecutionPolicy Bypass -File \"${env.NGROK_SCRIPT_PATH}\""
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "🐳 Building Docker image ${DOCKER_IMAGE}:${DOCKER_TAG}..."
                    bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Test Docker Image') {
            steps {
                script {
                    echo "🧪 Testing Docker image..."
                    // Replace with actual test logic later, for now just check if the container runs
                    bat "docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG} echo '✅ Container ran successfully'"
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        echo "🔐 Logging in and pushing Docker image to Docker Hub..."
                        bat "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                        bat "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    }
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                script {
                    echo "🚀 Deploying Docker container..."
                    // Stop and remove existing container if it exists
                    bat "docker rm -f ${DOCKER_IMAGE}-${DOCKER_TAG} || true"
                    // Run new container
                    bat "docker run -d --name ${DOCKER_IMAGE}-${DOCKER_TAG} -p 8080:8080 ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        stage('Clean Up Docker Images') {
            steps {
                script {
                    echo "🧹 Cleaning up Docker image..."
                    bat "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} || true"
                }
            }
        }
    }

    post {
        always {
            echo "♻️ Post-cleanup steps..."
            bat "docker container prune -f"
            bat "docker image prune -f"
        }
    }
}
