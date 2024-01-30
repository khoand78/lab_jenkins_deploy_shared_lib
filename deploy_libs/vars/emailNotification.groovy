def sendEmailNotification() {
    emailext (
        to: "khoadnguyen178@gmail.com",
        subject: "Jenkins build: ${currentBuild.currentResult}: ${env.STAGE_NAME}",
        body: "${currentBuild.currentResult}: Job ${env.STAGE_NAME}\nFor more information, click here: ${env.BUILD_URL}",
    )
}