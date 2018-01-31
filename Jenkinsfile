node {
    cleanWs()

    stage("checkout") {
        withEnv(['HTTPS_PROXY=http://webproxy-utvikler.nav.no:8088']) {
            sh(script: "git clone https://github.com/navikt/samordning-wsdl.git .")
        }
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
            sh "mvn -B -V -fn deploy -DskipTests"
        }
    }
}
