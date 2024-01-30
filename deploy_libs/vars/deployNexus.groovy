def call(String branch) {
    pom = readMavenPom file: "pom.xml";
    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
    artifactPath = filesByGlob[0].path;
    artifactExists = fileExists artifactPath;
    if(artifactExists) {
        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
        nexusArtifactUploader(
            nexusVersion: "nexus3",
            protocol: "http",
            nexusUrl: "192.168.56.158:8081",
            version: "${env.TAG_NAME}",
            groupId: "${branch}",
            repository: "maven-nexus-repo",
            credentialsId: "nexus-user-credentials",
            artifacts: [
                [artifactId: pom.artifactId,
                classifier: '',
                file: artifactPath,
                type: pom.packaging],
                [artifactId: pom.artifactId,
                classifier: '',
                file: "pom.xml",
                type: "pom"]
            ]
        );
    } else {
        error "*** File: ${artifactPath}, could not be found";
    }
}
