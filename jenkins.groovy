
pipeline {
    agent any
    environment {
        GIT_URL = "https://github.com/jungkimhoon/seed.git"
        GIT_ID = "ing2895@naver.com"
        WEB_ROOT_PATH = "$WORKSPACE/seed"
        WEBAPP_PATH = "$WEB_ROOT_PATH/src/main/webapp"
        DOCKER_ROOT_PATH = "/home/docker_admin/docker/seed"
        DOCKER_BUILD_PATH = "$DOCKER_ROOT_PATH/web/openjdk8"
        SSH_CONFIG_NAME = "Docker Container Server
    }
    tools {
        nodejs 'node14'
    	maven 'maven'
        jdk 'java8'
    }
    stages {
        stage('checkout') {
            steps {
                checkout([
                        $class: 'GitSCM', 
                        branches: [[name: '*/master']], 
                        doGenerateSubmoduleConfigurations: false, 
                        userRemoteConfigs: [[
                                credentialsId: "${GIT_ID}", 
                                url: "${GIT_URL}"
                            ]]
                        ])
                }
        }
        stage('Build') {
            stages {
                           
                stage('build - npm install') {
                    steps {
                        sh 'cd ${WEBAPP_PATH} && npm install'
                    }
                }
                
                stage('build - run build') {
                    steps {		               
                       sh 'cd ${WEBAPP_PATH} && npm run build:prod --max-old-space-size=8000'
                    }
                }
                
                stage('build - maven') {
                    steps {
                        sh 'mvn -f $WEB_ROOT_PATH/pom.xml clean install'
						            sh 'mvn -f $EXPORTER_WEB_ROOT_PATH/pom.xml clean install'
                    }
                }

                stage('build - copy') {
                    steps {
                        sh 'cp -Rf "$WEB_ROOT_PATH/target/inno-user_defined_report-web-0.0.5.jar" "$DOCKER_BUILD_PATH/user_defined_report.jar"'
                    }
                }
                
            }
        }

        stage('Docker') {
            stages {
                stage('docker - compose rm') {
                    steps {
                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: "${SSH_CONFIG_NAME}", 
                                    transfers: [
                                        sshTransfer(
                                            cleanRemote: false, 
                                            excludes: '', 
                                            execCommand: "cd ${DOCKER_ROOT_PATH} && docker-compose rm -fsv web", 
                                        )
                                    ]
                                )
                            ]
                        )
                    }
                }

                stage('docker - image') {
                    steps {
                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: "${SSH_CONFIG_NAME}", 
                                    transfers: [
                                        sshTransfer(
                                            cleanRemote: false, 
                                            excludes: '', 
                                            execCommand: "cd \"${EXPORTER_DOCKER_BUILD_PATH}\" && sh Dockerbuild.sh $BUILD_NUMBER && cd \"${DOCKER_BUILD_PATH}\" && sh Dockerbuild.sh $BUILD_NUMBER && docker image prune -f",
                                        )
                                    ]
                                )
                            ]
                        )
                    }
                }
                
                stage('docker - compose up') {
                    steps {
                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: "${SSH_CONFIG_NAME}", 
                                    transfers: [
                                        sshTransfer(
                                            cleanRemote: false, 
                                            excludes: '', 
                                            execCommand: "cd ${DOCKER_ROOT_PATH} && docker-compose up -d web", 
                                        )
                                    ]
                                )
                            ]
                        )
                    }
                }
            }
        }
   }
}
