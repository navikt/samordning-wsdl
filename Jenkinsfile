#!/usr/bin/env groovy
@Library('peon-pipeline') _

node {
    def commitHash
    try {
        cleanWs()

        stage("checkout") {
            withCredentials([string(credentialsId: 'navikt-ci-oauthtoken', variable: 'GITHUB_OAUTH_TOKEN')]) {
                sh "git init"
                sh "git pull https://${GITHUB_OAUTH_TOKEN}:x-oauth-basic@github.com/navikt/samordning-wsdl.git"
            }

            commitHash = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
            github.commitStatus("navikt-ci-oauthtoken", "navikt/samordning-wsdl", 'continuous-integration/jenkins', commitHash, 'pending', "Build #${env.BUILD_NUMBER} has started")
        }

        stage("build") {
            def mvnHome = tool "maven-3.3.9"
            withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                sh "mvn -B -V verify"
            }
        }

        stage("deploy") {
            def mvnHome = tool "maven-3.3.9"
            withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                sh "mvn -B -V -fn deploy -DskipTests -Dmaven.install.skip=true"
            }
        }

        github.commitStatus("navikt-ci-oauthtoken", "navikt/samordning-wsdl", 'continuous-integration/jenkins', commitHash, 'success', "Build #${env.BUILD_NUMBER} has finished")
    } catch (err) {
        github.commitStatus("navikt-ci-oauthtoken", "navikt/samordning-wsdl", 'continuous-integration/jenkins', commitHash, 'failure', "Build #${env.BUILD_NUMBER} has failed")

        throw err
    }
}
