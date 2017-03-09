FROM node
WORKDIR /root
ADD package.json /root/stage/
ADD yarn.lock /root/stage/
RUN yarn install
ADD * /root/stage/
ADD build_and_publish.sh /
RUN chmod +x /build_and_publish.sh
