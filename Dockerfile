FROM crystallang/crystal
CMD ["bash"]
ADD *.cr /tmp/
WORKDIR /tmp
RUN crystal spec
WORKDIR /root
RUN crystal build /tmp/main.cr -o sdkgen2 && rm /tmp/*.cr
RUN cp sdkgen2 /usr/bin