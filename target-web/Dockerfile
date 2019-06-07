FROM node
WORKDIR /root/stage/
ADD package.json /root/stage/
ADD package-lock.json /root/stage/
RUN npm ci
ADD . /root/stage/
ADD build_and_publish.sh /
RUN chmod +x /build_and_publish.sh
