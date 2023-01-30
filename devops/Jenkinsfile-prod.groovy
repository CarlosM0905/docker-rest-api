pipeline {
    agent any
    
    tools {nodejs "node"} 
    
    stages {
        stage('Cloning code') {
            steps {
                // Get code from a GitHub repository
                git 'https://github.com/CarlosM0905/docker-rest-api.git'
            }
        }
        
        stage('Install dependencies') {
            steps {
                bat 'npm install'
            }
        }
    
        stage('Validate node') {
            steps {
                bat 'npm config ls'
            }
        }
        
        stage('Execute tests') {
            steps {
                bat 'npm run test'
            }
        }
        
        stage('Sonarqube Analysis') {
            steps {
               script {
                    def scannerHome = tool 'SonarQube Scanner';
                        withSonarQubeEnv("SonarCloud") {
                        bat "${tool('SonarQube Scanner')}/bin/sonar-scanner \
                        -Dsonar.projectKey=CarlosM0905_docker-rest-api \
                        -Dsonar.sources=. \
                        -Dsonar.organization=carlosm0905 \
                        -Dsonar.css.node=. \
                        -Dsonar.host.url=https://sonarcloud.io/ \
                        -Dsonar.login=ba01acd98b808c9c60f695808934e9c9a1945275"
                    }
                }
            }
        }
        
        stage('Docker build') {
            steps {
                bat 'docker build -t carlosm0905/proyect-rest-api .'
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'pass', usernameVariable: 'user')]) {
                    // the code here can access $pass and $user
                    bat "docker login -u ${env.user} -p ${env.pass}"
                    echo 'Login Completed'
                    bat 'docker push carlosm0905/proyect-rest-api:latest'
                    echo 'Push Image Completed'
                }
            }
        }
    }

    post{
        always {  
            bat 'docker logout'           
            }      
    }      
}