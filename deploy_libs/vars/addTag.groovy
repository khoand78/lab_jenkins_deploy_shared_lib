import java.text.SimpleDateFormat
import java.util.Date

def call(String branchName) {
    withCredentials([gitUsernamePassword(credentialsId: 'gitlab-user-ci')]){
        def gitHash = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()

        def commitDate = sh(script: 'git show -s --format=%ci HEAD', returnStdout: true).trim()
        def dateFormat = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z')
        def parsedDate = dateFormat.parse(commitDate)
        def formattedDate = new SimpleDateFormat('yyyyMMdd').format(parsedDate)

        sh "echo ${gitHash}"
        sh "echo ${formattedDate}"

        if(branchName == 'main') {
            env.TAG_NAME = "${formattedDate}-release"
            sh "git tag ${env.TAG_NAME}"
            sh "git push origin ${env.TAG_NAME}"
        }

        if(branchName == 'uat') {
            env.TAG_NAME = "${formattedDate}-uat-${gitHash}"
            sh "git tag ${env.TAG_NAME}"
            sh "git push origin ${env.TAG_NAME}"
        }
    }
}