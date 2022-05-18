#!/bin/bash

target=$1
count=$2
index=0

extension="${target##*.}"
name="${target%.*}"

while ((index++ < count)); do
  cp "$target" "${name}_$index.$extension"
done
