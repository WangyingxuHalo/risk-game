#!/bin/bash

# first terminate any old ones
docker kill citest-651
docker rm citest-651

#create volume
docker volume create riscvol
# now run the new one
docker run -d --name citest-651 -p 1651:1651 -v riscvol:/home/juser/risc --restart always -t citest ./gradlew server:run

