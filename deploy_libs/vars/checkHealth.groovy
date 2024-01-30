def call(String port) {
    sshagent(['deploy_server']) {
        timeout(time: 60, unit: 'SECONDS') {
            waitUntil {
                script {
                    try {         
                        sh "ssh -o StrictHostKeyChecking=no -l osboxes 192.168.56.164 curl -s --head --request GET 192.168.56.164:${port}/actuator/health | grep '200'"
                        return true
                    } catch (Exception e) {
                        return false
                    }
                }
            }
        }
    }
}