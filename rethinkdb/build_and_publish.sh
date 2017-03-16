#!/usr/bin/env bash

VERSION=1.0.$1

export PATH=$(npm bin):$PATH
npm version $VERSION || true
tsc
babel rethinkdb.js -o rethinkdb.js
npm publish
