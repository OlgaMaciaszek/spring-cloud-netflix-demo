#!/bin/bash

set -o errexit

function kill_app() {
	local appName="${1}"
	pkill -9 "${appName}" && echo "Killed ${appName}"|| echo "Nothing to kill"
}

function run_app() {
	local appName="${1}"
	pushd "${appName}/target"
		nohup java -jar "${appName}"*.jar > nohup.log &
		echo "Running app [${appName}]. Logs are here [$(pwd)]"
	popd
}


echo "Killing running apps"

kill_app eureka-server
kill_app fraud-verifier
kill_app ignored-service
kill_app turbine
kill_app user-service
kill_app zuul-proxy

echo "Building the apps"

./mvnw clean install

echo "Running the apps"

run_app eureka-server
run_app fraud-verifier
run_app ignored-service
run_app turbine
run_app user-service
run_app zuul-proxy

WAIT_TIME=30
echo "Waiting for [${WAIT_TIME}] seconds for apps to start"
sleep "${WAIT_TIME}"