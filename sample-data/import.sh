#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
pushd $DIR

if [ "$1" == "docker" ]; then
  echo 'Docker install'
  docker cp product.json mongodb:/.
  docker cp inventory.json mongodb:/.

  docker exec mongodb mongoimport --db starter --collection product --drop --file product.json --jsonArray
  docker exec mongodb mongoimport --db starter --collection inventory --drop --file inventory.json --jsonArray
else
  echo 'Host install'
  mongoimport --db starter --collection product --drop --file product.json --jsonArray
  mongoimport --db starter --collection inventory --drop --file inventory.json --jsonArray
fi
