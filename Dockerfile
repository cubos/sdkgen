FROM crystallang/crystal:0.27.0
ADD . /tmp/
WORKDIR /tmp
RUN crystal tool format --check
RUN crystal spec
RUN crystal build main.cr -o sdkgen

FROM crystallang/crystal:0.27.0
CMD ["bash"]
COPY --from=0 /tmp/sdkgen /usr/bin
