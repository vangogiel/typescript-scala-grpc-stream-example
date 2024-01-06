#!/bin/bash

PROTO_DIR=$1
OUT_DIR=./src/protobuf

rm -rf ${OUT_DIR}
mkdir -p ${OUT_DIR}

npx protoc \
  --ts_out ${OUT_DIR} \
  --ts_opt long_type_number \
  --proto_path ../backend/protobuf/src/main/protobuf \
  $(find ../backend/protobuf/src/main/protobuf -name '*.proto')
  