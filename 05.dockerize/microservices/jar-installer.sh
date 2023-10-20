#!/bin/bash

echo "Installing config-server..."
cd ./config-server
mvn clean install
cd ..

echo "Installing naming-server..."
cd ./naming-server
mvn clean install
cd ..

echo "Installing gateway-server..."
cd ./gateway-server
mvn clean install
cd ..

echo "Installing products-service..."
cd ./products-service
mvn clean install
cd ..

echo "Installing orders-service..."
cd ./orders-service
mvn clean install
cd ..

echo "Installing users-management-service..."
cd ./users-management-service
mvn clean install
cd ..