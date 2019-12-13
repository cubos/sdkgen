
FROM crystallang/crystal:0.31.1
ADD . /tmp/
WORKDIR /tmp
RUN crystal tool format --check
RUN crystal spec
RUN crystal build main.cr -o sdkgen

FROM crystallang/crystal:0.31.1
CMD ["bash"]
COPY --from=0 /tmp/sdkgen /usr/bin
