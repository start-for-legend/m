#!/bin/bash
CONFIG_PATH="/home/ubuntu/docker-compose.yml"
INIT_FILE="/home/ubuntu/.hyewon"

sudo docker-compose pull

if [ ! -f $INIT_FILE ]; then
    echo "First time running...."
    sudo docker-compose -f $CONFIG_PATH up -d mysql redis
    touch $INIT_FILE
fi

sudo docker-compose -f $CONFIG_PATH up -d --no-deps app nginx certbot

sudo docker image prune -af
