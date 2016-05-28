#!/bin/bash
protoc --javamicro_out=src -Ipb/channel `find pb/channel -name '*.proto'|tr '\n' ' '` 
protoc --javamicro_out=src -Ipb/im `find pb/im -name '*.proto'|tr '\n' ' '` 