def call(String port) {
    sshagent(['deploy_server']) {
        sh 'echo "Pull artifact from Nexus"'
        sh "ssh -o StrictHostKeyChecking=no -l osboxes 192.168.56.164 wget --user=admin --password=qwert123456 \"http://192.168.56.158:8081/repository/maven-nexus-repo/uat/spring-petclinic/${env.TAG_NAME}/spring-petclinic-${env.TAG_NAME}.jar\""
    }
                
    sshagent(['deploy_server']) {
        sh 'echo "Deploy artifact"'
        sh "ssh -o StrictHostKeyChecking=no -l osboxes 192.168.56.164 nohup java -Dserver.port=${port} -jar spring-petclinic-${env.TAG_NAME}.jar &"
    }
}