FROM crystallang/crystal
CMD ["bash"]
ADD . /tmp/
WORKDIR /tmp
RUN crystal tool format --check
RUN crystal spec
WORKDIR /root
RUN crystal build /tmp/main.cr -o sdkgen
RUN cp sdkgen /usr/bin
