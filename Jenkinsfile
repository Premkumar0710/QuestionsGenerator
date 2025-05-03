pipeline {
    agent any

    stages {
        stage('Clone Repo') {
            steps {
                git branch: 'feature-branch',
                    url: 'https://github.com/Premkumar0710/QuestionsGenerator.git'
            }
        }

        stage('Check Commit Message Format') {
            steps {
                script {
                    def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()
                    echo "Latest Commit Message: ${commitMessage}"

                    if (!commitMessage.matches("^(feat|fix|chore|refactor|docs|test|style):\\d{4}-.+")) {
                        error("❌ Commit message does not follow required format: <type>:0000-description")
                    } else {
                        echo "✅ Commit message format is valid"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                echo '🏗️ Building your project...'
                // Add your build logic here
            }
        }
    }
}
