#!/bin/bash

set -o errexit

http POST localhost:9080/application < cardApplication.json