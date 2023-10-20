#!/bin/bash

echo "Creating products-service image..."
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ricardoahumada/products-service:sb3-a
