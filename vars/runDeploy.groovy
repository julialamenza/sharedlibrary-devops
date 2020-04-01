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
                        stage ('Test Env') {
                              script {
                                try {
                                //Define some vars
                                def remote = [:]
                                remote.name = 'dev-www01'
                                remote.host = 'dev-www01.dev.something.com'
                                def deployment_env = "test"

                                //Allow to get BUILD_USER_ID
                                wrap([$class: 'BuildUser']) {
                                withCredentials([sshUserPrivateKey(credentialsId: 'credentialId', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                remote.user = userName
                                remote.identityFile = identity
                                remote.allowAnyHosts = true
                                sshCommand remote: remote, command: "your command"
                                  }
                                }
                                }
                              catch (exec)  {
                              echo "Deploy Failed!"
                              currentBuild.result = 'UNSTABLE'
                              warnError('Set Build UNSTABLE') {
                                  sh('false')
                              }
                              }
                              }
                          }
                      }

                      if ( "${environment}"== 'staging'){
                          stage('Check if ITCC is Approved By SRE') {
                                script {
                                  try {
                                  // Define some vars
                                  def remote = [:]
                                  remote.name = 'deploy'
                                  remote.host = 'deploy.cc.something.com'
                                  def deployment_env = "staging"
                                  //Allow to get BUILD_USER_ID
                                  wrap([$class: 'BuildUser']) {
                                  withCredentials([sshUserPrivateKey(credentialsId: 'credentialId', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                  remote.user = userName
                                  remote.identityFile = identity
                                  remote.allowAnyHosts = true
                                  sshCommand remote: remote, command: "your command"
                                    }
                                  }
                                  }
                                  catch (exec)  {
                                  echo "FAILURE - ITCC not approved, please ask SRE to approve!"
                                  currentBuild.result = 'UNSTABLE'
                                  warnError('Set Build UNSTABLE') {
                                      sh('false')
                                      }
                                     // throw exec
                                  }
                                }
                           }
                          stage ('Staging Env') {
                                 script {
                                    try {
                                    // Define some vars
                                    def remote = [:]
                                    remote.name = 'deploy'
                                    remote.host = 'deploy.cc.something.com'
                                    def deployment_env = "staging"
                                    //Allow to get BUILD_USER_ID
                                    wrap([$class: 'BuildUser']) {
                                    withCredentials([sshUserPrivateKey(credentialsId: 'credentialId', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                    remote.user = userName
                                    remote.identityFile = identity
                                    remote.allowAnyHosts = true
                                    sshCommand remote: remote, command: "your-command"
                                      }
                                    }
                                    }
                                  catch (exec)  {
                                  echo "Deploy Failed!"
                                  currentBuild.result = 'UNSTABLE'
                                  warnError('Set Build UNSTABLE') {
                                      sh('false')
                                  }
                                  }
                                  }
                            }
                        }

                        if ( "${environment}"== 'production'){
                            stage('Check if ITCC is Approved By SRE') {
                                  script {
                                  try {
                                  // Define some vars
                                  def remote = [:]
                                  remote.name = 'deploy'
                                  remote.host = 'deploy.cc.something.com'
                                  def deployment_env = "staging"
                                  //Allow to get BUILD_USER_ID
                                  wrap([$class: 'BuildUser']) {
                                  withCredentials([sshUserPrivateKey(credentialsId: 'credentialId', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                  remote.user = userName
                                  remote.identityFile = identity
                                  remote.allowAnyHosts = true
                                  sshCommand remote: remote, command: "your-command"
                                    }
                                  }
                                  }
                                  catch (exec)  {
                                  echo "FAILURE - ITCC not approved, please ask SRE to approve!"
                                  currentBuild.result = 'UNSTABLE'
                                  warnError('Set Build UNSTABLE') {
                                    sh('false')
                                    }
                                    //throw exec
                                  }
                                  }
                            }
                            stage ('Production Env') {
                                    script {
                                        try {
                                        // Define some vars
                                        def remote = [:]
                                        remote.name = 'deploy'
                                        remote.host = 'deploy.cc.something.com'
                                        def deployment_env = "live"
                                        //Allow to get BUILD_USER_ID
                                        wrap([$class: 'BuildUser']) {
                                        withCredentials([sshUserPrivateKey(credentialsId: 'credentialId', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
                                        remote.user = userName
                                        remote.identityFile = identity
                                        remote.allowAnyHosts = true
                                        sshCommand remote: remote, command: "your-command"
                                          }
                                        }
                                        }
                                      catch (exec)  {
                                      echo "Deploy Failed!"
                                      currentBuild.result = 'UNSTABLE'
                                      warnError('Set Build UNSTABLE') {
                                          sh('false')
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