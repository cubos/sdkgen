#!/usr/bin/env bash

VERSION=1.0.$1

export PATH=$(npm bin):$PATH
npm version $VERSION || true
tsc
babel index.js -o index.js
npm publish
