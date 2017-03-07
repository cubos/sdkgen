FROM crystallang/crystal
WORKDIR /root
ADD *.cr /tmp/
RUN crystal build /tmp/main.cr -o sdkgen2 && rm /tmp/*.cr
CMD ["bash"]