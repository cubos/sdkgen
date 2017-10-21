FROM crystallang/crystal
CMD ["bash"]
ADD . /tmp/
WORKDIR /tmp
RUN crystal spec
WORKDIR /root
RUN crystal build /tmp/main.cr -o sdkgen2
RUN cp sdkgen2 /usr/bin