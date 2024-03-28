#!/bin/bash
CONFIG_PATH="/home/ubuntu/docker-compose.yml"

sudo docker-compose pull

if [ ! -e /home/ubuntu/.initialized ];then
  echo "First time running"
  sudo docker-compose -f $CONFIG_PATH up -d redis mysql
  touch /home/ubuntu/.initialized
fi

sudo docker-compose -f $CONFIG_PATH up -d app

sudo docker image prune -af
