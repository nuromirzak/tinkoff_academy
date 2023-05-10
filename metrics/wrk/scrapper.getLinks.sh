#!/bin/bash
URL=http://localhost:8000/links
CHAT_ID_HEADER=Tg-Chat-Id
CHAT_ID=362037700

THREADS=10
CONNECTIONS=100
DURATION=30s

wrk -t$THREADS -c$CONNECTIONS -d$DURATION -H "$CHAT_ID_HEADER: $CHAT_ID" $URL
