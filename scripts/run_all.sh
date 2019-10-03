#!/bin/bash

set -o errexit

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

./scripts/kill_all.sh

echo "Building the apps"

./mvnw clean install -T 4

echo "Running the apps"

run_app eureka-server

wait 10

run_app card-service
run_app fraud-verifier
run_app ignored-service
run_app user-service
run_app gateway-proxy

wait 120

echo "Sending a request"

./scripts/send_request.sh
wait 5
./scripts/send_request.sh
wait 5
./scripts/send_request.sh