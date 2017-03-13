#!/usr/bin/env bash

NAME=$(basename `git rev-parse --show-toplevel`)
VERSION=1.0.$2
LOCATION=/root

ls -lA target

rm -rf $LOCATION/api/src/main/java/io/cubos/api
mkdir -p $LOCATION/api/src/main/java/io/cubos/api/$NAME
cp $1 $LOCATION/tmp.java
cd $LOCATION

pwd
tree

(echo "package io.cubos.api."$NAME";"; cat tmp.java) > api/src/main/java/io/cubos/api/$NAME/API.java

CURRENT_NAME=$(cat api/src/main/res/values/strings.xml | grep app_name | sed -e 's/<[^>]*>//g' | xargs echo -n)
CURRENT_VERSION=$(cat api/build.gradle | grep "version " | sed 's/version//g' | xargs echo -n)
sed -i -e 's/'$CURRENT_NAME'/'$NAME'/g' api/src/main/java/io/cubos/api/$NAME/API.java
sed -i -e 's/'$CURRENT_NAME'/'$NAME'/g' api/src/main/AndroidManifest.xml
sed -i -e 's/'$CURRENT_NAME'/'$NAME'/g' api/src/main/res/values/strings.xml
sed -i -e 's/'$CURRENT_NAME'/'$NAME'/g' api/build.gradle
sed -i -e 's/version '"'"$CURRENT_VERSION"'"'/version '"'"$VERSION"'"'/g' api/build.gradle

./gradlew assembleRelease publish
