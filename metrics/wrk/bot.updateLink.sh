#!/bin/bash
URL=http://localhost:9000
LUA_SCRIPT_PATH=bot.updateLink.lua

THREADS=10
CONNECTIONS=100
DURATION=30s

if [[ ! -f $LUA_SCRIPT_PATH ]]; then
  echo "Error: Lua script file '$LUA_SCRIPT_PATH' does not exist. Exiting..."
  exit 1
fi

wrk -t$THREADS -c$CONNECTIONS -d$DURATION -s $LUA_SCRIPT_PATH $URL
