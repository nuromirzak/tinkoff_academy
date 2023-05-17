#!/bin/bash

if ! mvn clean install -DskipTests; then
    echo "Maven build failed"
    exit 1
fi

if ! (cd scrapper && docker compose up -d); then
    echo "Docker compose failed"
    cd ..
    exit 1
fi

cd ..

mvn spring-boot:run -pl scrapper &
mvn spring-boot:run -pl bot &
