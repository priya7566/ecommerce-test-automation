pipeline {
    agent any

    /* ─── Build Parameters (visible in Jenkins UI "Build with Parameters") ─── */
    parameters {
        choice(name: 'BROWSER',     choices: ['chrome', 'firefox', 'edge'],  description: 'Browser to run tests on')
        choice(name: 'ENVIRONMENT', choices: ['staging', 'dev', 'prod'],      description: 'Target environment')
        choice(name: 'TAGS',        choices: ['@regression', '@smoke', '@negative', '@data-driven', '@cart-validation'],
                                                                               description: 'Cucumber tag filter')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run browser in headless mode')
    }

    /* ─── Environment Variables ─── */
    environment {
        MAVEN_OPTS = '-Xmx1024m'
        REPORT_DIR = 'reports'
    }

    /* ─── Pipeline Stages ─── */
    stages {

        stage('Checkout SCM') {
            steps {
                echo "📥 Pulling source from Git..."
                checkout scm
            }
        }

        stage('Validate & Compile') {
            steps {
                echo "🔨 Compiling project..."
                sh 'mvn clean compile test-compile -q'
            }
        }

        stage('Run Tests') {
            steps {
                echo "🧪 Running Cucumber tests | Browser: ${params.BROWSER} | Tags: ${params.TAGS} | Env: ${params.ENVIRONMENT}"
                sh """
                    mvn test \
                        -Dbrowser=${params.BROWSER} \
                        -Denv=${params.ENVIRONMENT} \
                        -Dheadless=${params.HEADLESS} \
                        -Dcucumber.filter.tags="${params.TAGS}" \
                        -Dfailsafe.rerunFailingTestsCount=1
                """
            }
            post {
                always {
                    echo "📊 Archiving test results..."
                }
            }
        }

        stage('Publish Reports') {
            steps {
                // Publish Cucumber HTML Report
                publishHTML(target: [
                    allowMissing         : false,
                    alwaysLinkToLastBuild: true,
                    keepAll              : true,
                    reportDir            : 'reports',
                    reportFiles          : 'cucumber-html-report.html',
                    reportName           : 'Cucumber HTML Report'
                ])

                // Archive JSON for downstream processing
                archiveArtifacts artifacts: 'reports/cucumber.json', fingerprint: true

                // Publish JUnit results for Jenkins test trend graph
                junit 'reports/cucumber.xml'
            }
        }
    }

    /* ─── Post Pipeline Actions ─── */
    post {
        success {
            echo "✅ All tests PASSED! Build: ${env.BUILD_NUMBER}"
            emailext(
                subject: "✅ [PASS] Automation Build #${env.BUILD_NUMBER} - ${params.TAGS}",
                body: """
                    <h2>Build Successful</h2>
                    <p><b>Build #:</b> ${env.BUILD_NUMBER}</p>
                    <p><b>Tags:</b> ${params.TAGS}</p>
                    <p><b>Browser:</b> ${params.BROWSER}</p>
                    <p><b>Environment:</b> ${params.ENVIRONMENT}</p>
                    <p>View Report: <a href="${env.BUILD_URL}Cucumber_20HTML_20Report">Click Here</a></p>
                """,
                mimeType: 'text/html',
                to: "${env.TEAM_EMAIL}"
            )
        }
        failure {
            echo "❌ Tests FAILED. Build: ${env.BUILD_NUMBER}"
            emailext(
                subject: "❌ [FAIL] Automation Build #${env.BUILD_NUMBER} - ${params.TAGS}",
                body: """
                    <h2>Build Failed</h2>
                    <p><b>Build #:</b> ${env.BUILD_NUMBER}</p>
                    <p><b>Tags:</b> ${params.TAGS}</p>
                    <p>View Logs: <a href="${env.BUILD_URL}console">Console Output</a></p>
                    <p>View Report: <a href="${env.BUILD_URL}Cucumber_20HTML_20Report">Test Report</a></p>
                """,
                mimeType: 'text/html',
                to: "${env.TEAM_EMAIL}"
            )
        }
        always {
            cleanWs()
        }
    }
}
