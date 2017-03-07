NAME=$(basename `git rev-parse --show-toplevel`)
VERSION=1.0.$(git rev-list --count HEAD)

cp $1 /root/api.ts
cd /root

npm-exec json -I -f package.json -e 'this.name=\"@cubos/'$NAME'\"'
npm version $VERSION || true
npm-exec tsc
npm-exec babel api.js -o api.js
npm publish
