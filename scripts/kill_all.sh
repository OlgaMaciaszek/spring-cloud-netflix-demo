#!/bin/bash

set -o errexit

function kill_app() {
	local appName="${1}"
	pkill -9 -f "${appName}" && echo "Killed ${appName}"|| echo "Couldn't kill [${appName}]"
}

echo "Killing running apps"

kill_app card-service
kill_app eureka-server
kill_app fraud-verifier
kill_app ignored-service
kill_app turbine
kill_app user-service
kill_app zuul-proxy
kill_app gateway-proxy