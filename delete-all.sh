#!/bin/bash

docker kill book_store_db
docker rm book_store_db


docker system prune -f

rm -rf ~/docker-data/