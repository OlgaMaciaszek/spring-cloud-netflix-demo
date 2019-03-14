#!/bin/bash

set -o errexit

function kill_app() {
	local appName="${1}"
	pkill -9 -f "${appName}" && echo "Killed ${appName}"|| echo "Couldn't kill [${appName}]"
}

function run_app() {
	local appName="${1}"
	pushd "${appName}/target"
		nohup java -jar "${appName}"*.jar > nohup.log &
		echo "Running app [${appName}]. Logs are here [$(pwd)/nohup.log]"
	popd
}

function wait() {
	local waitTime="${1}"
	echo "Waiting for [${waitTime}] seconds"
	sleep "${waitTime}"
}


echo "Killing running apps"

kill_app card-service
kill_app eureka-server
kill_app fraud-verifier
kill_app ignored-service
kill_app turbine
kill_app user-service
kill_app zuul-proxy

echo "Building the apps"

./mvnw clean install -T 4

echo "Running the apps"

run_app eureka-server

wait 10

run_app card-service
run_app fraud-verifier
run_app ignored-service
run_app turbine
run_app user-service
run_app zuul-proxy

wait 40

echo "Sending a request"

./scripts/send_request.sh
wait 5
./scripts/send_request.sh
wait 5
./scripts/send_request.sh