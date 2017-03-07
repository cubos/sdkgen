FROM node
WORKDIR /root
ADD package.json /root/
ADD yarn.lock /root/
RUN yarn install
ADD * /root/
ADD build_and_publish.sh /
RUN chmod +x /build_and_publish.sh
