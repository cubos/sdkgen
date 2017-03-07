FROM crystallang/crystal
CMD ["bash"]
WORKDIR /root
ADD *.cr /tmp/
RUN crystal build /tmp/main.cr -o sdkgen2 && rm /tmp/*.cr
RUN cp sdkgen2 /usr/bin