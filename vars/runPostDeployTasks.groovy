def call(String app_name) {

    pipeline {
        //agent any
        agent { label 'cc-jenkinagen01' }


        parameters {
                string(defaultValue: '', name: 'version', description: "Tag version to release")
                string(defaultValue: '', name: 'jira_issue_id', description: "ITCC Ticket")
                string(defaultValue: '', name: 'environment', description: "Environment to deploy(test,staging,production)")
        }

        options {
            // Only keep the 10 most recent builds
            buildDiscarder(logRotator(numToKeepStr:'10'))
        }

        stages {
                stage ('Initiate Deploy') {
                steps {
                    script {
                    if ( "${version}" == '' || "${environment}" == ''){
                        echo "Version or Environment not set"
                        warnError('Set Build UNSTABLE') { sh('false') }
                    }
                        if ( "${environment}"== 'test'){
                            stage ('Released to Test Env') {
                                script {
                                    //Define some vars
                                    def remote = [:]
                                    remote.name = 'dev-www01'
                                    remote.host = 'dev-www01.something.com'
                                    def deployment_env = "test"
                                    //Allow to get BUILD_USER_ID
                                    wrap([$class: 'BuildUser']) {
                                    withCredentials([sshUserPrivateKey(credentialsId: 'user_id	', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                    remote.user = userName
                                    remote.identityFile = identity
                                    remote.allowAnyHosts = true
                                    sshCommand remote: remote, command: "your command", failOnError: false
                                    }
                                    }
                                }
                            }
                        }

                        if ( "${environment}"== 'staging'){
                            stage('Released to Staging Env') {
                                    script {
                                    //Define some vars
                                    def remote = [:]
                                    remote.name = 'dev-www01'
                                    remote.host = 'dev-www01.something.com'
                                    def deployment_env = "staging"
                                    //Allow to get BUILD_USER_ID
                                    wrap([$class: 'BuildUser']) {
                                    withCredentials([sshUserPrivateKey(credentialsId: 'user_id	', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                    remote.user = userName
                                    remote.identityFile = identity
                                    remote.allowAnyHosts = true
                                    sshCommand remote: remote, command: "your command", failOnError: false
                                    }
                                    }
                                }
                            }
                        }
                            stage ('Run Nessus Scan') {
                                    script {
                                    //Define some vars
                                    def remote = [:]
                                    remote.name = 'dev-www01'
                                    remote.host = 'dev-www01.something.com'
                                    def deployment_env = "staging"
                                    //Allow to get BUILD_USER_ID
                                    wrap([$class: 'BuildUser']) {
                                    withCredentials([sshUserPrivateKey(credentialsId: 'user_id	', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                    remote.user = userName
                                    remote.identityFile = identity
                                    remote.allowAnyHosts = true
                                    sshCommand remote: remote, command: "your command", failOnError: false
                                    }
                                    }
                                }
                            }
                        }

                            if ( "${environment}"== 'production'){
                                stage('Released to Production Env') {
                                    script {
                                    //Define some vars
                                    def remote = [:]
                                    remote.name = 'dev-www01'
                                    remote.host = 'dev-www01.something.com'
                                    def deployment_env = "prod"
                                    //Allow to get BUILD_USER_ID
                                    wrap([$class: 'BuildUser']) {
                                    withCredentials([sshUserPrivateKey(credentialsId: 'user_id	', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                    remote.user = userName
                                    remote.identityFile = identity
                                    remote.allowAnyHosts = true
                                    sshCommand remote: remote, command: "your command", failOnError: false
                                    }
                                    }
                                }
                            }
                        }
                                stage ('Run Nessus Scan') {
                                        script {
                                            // Define some vars
                                            def remote = [:]
                                            remote.name = 'deploy01'
                                            remote.host = 'deploy01.something.com'
                                            def deployment_env = "live"
                                            //Allow to get BUILD_USER_ID
                                            wrap([$class: 'BuildUser']) {
                                            withCredentials([sshUserPrivateKey(credentialsId: 'user_id	', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                            remote.user = userName
                                            remote.identityFile = identity
                                            remote.allowAnyHosts = true
                                            sshCommand remote: remote, command: "your command", failOnError: false
                                            }
                                            }
                                        }
                                    }
                            }
                    }

                    }
                }
            }
    }

}