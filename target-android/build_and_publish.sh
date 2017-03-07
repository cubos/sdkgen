#!/usr/bin/env bash

NAME=$(basename `git rev-parse --show-toplevel`)
VERSION=1.0.$(git rev-list --count HEAD)

# cp $1 /root/api.ts
# cd /root


./gradlew assembleRelease publish
