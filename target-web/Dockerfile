FROM node
WORKDIR /root
RUN npm install -g yarn
ADD package.json /root/
ADD yarn.lock /root/
RUN yarn install
ADD * /root/
ADD build_and_publish.sh /
