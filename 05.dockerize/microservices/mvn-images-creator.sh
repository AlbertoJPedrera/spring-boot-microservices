#!/bin/bash

echo "Creating config-server image..."
cd ./config-server
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ricardoahumada/config-server:sb3
cd ..

echo "Creating naming-server image..."
cd ./naming-server
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ricardoahumada/naming-server:sb3
cd ..

echo "Creating gateway-server image..."
cd ./gateway-server
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ricardoahumada/gateway-server:sb3
cd ..

echo "Creating products-service image..."
cd ./products-service
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ricardoahumada/products-service:sb3
cd ..

echo "Creating orders-service image..."
cd ./orders-service
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ricardoahumada/orders-service:sb3
cd ..

echo "Creating users-management-service image..."
cd ./users-management-service
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ricardoahumada/users-management-service:sb3
cd ..

docker images