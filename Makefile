# Makefile for VMS Spring Boot Project

# Variables
# Use the artifactId from your pom.xml
APP_NAME := vms
# Package path based on your groupId
BASE_PACKAGE_PATH := src/main/java/com/ivanazis/vms
# Docker image name
DOCKER_IMAGE := com.ivanazis/$(APP_NAME)
DOCKER_TAG := latest

# Default command
.DEFAULT_GOAL := help

## --------------------------------------
## Development
## --------------------------------------

.PHONY: run
run:
	@./mvnw spring-boot:run

.PHONY: clean
clean:
	@./mvnw clean

.PHONY: test
test:
	@./mvnw test

## --------------------------------------
## Scaffolding
## --------------------------------------
.PHONY: new-module
new-module:
ifndef name
	$(error name is not set. Usage: make new-module name=<module-name>)
endif
	@echo ">> Creating module: $(name)"
	$(eval PascalCaseName := $(shell echo $(name) | awk '{print toupper(substr($$0,1,1))substr($$0,2)}'))
	mkdir -p $(BASE_PACKAGE_PATH)/modules/$(name)/adapter/in/web/dto
	mkdir -p $(BASE_PACKAGE_PATH)/modules/$(name)/adapter/out/persistence
	mkdir -p $(BASE_PACKAGE_PATH)/modules/$(name)/application
	mkdir -p $(BASE_PACKAGE_PATH)/modules/$(name)/domain/model
	mkdir -p $(BASE_PACKAGE_PATH)/modules/$(name)/domain/port/in
	mkdir -p $(BASE_PACKAGE_PATH)/modules/$(name)/domain/port/out
	touch $(BASE_PACKAGE_PATH)/modules/$(name)/adapter/in/web/$(PascalCaseName)Controller.java
	touch $(BASE_PACKAGE_PATH)/modules/$(name)/adapter/out/persistence/$(PascalCaseName)Document.java
	touch $(BASE_PACKAGE_PATH)/modules/$(name)/adapter/out/persistence/$(PascalCaseName)MongoRepository.java
	touch $(BASE_PACKAGE_PATH)/modules/$(name)/adapter/out/persistence/$(PascalCaseName)RepositoryAdapter.java
	touch $(BASE_PACKAGE_PATH)/modules/$(name)/application/$(PascalCaseName)Service.java
	touch $(BASE_PACKAGE_PATH)/modules/$(name)/domain/model/$(PascalCaseName).java
	touch $(BASE_PACKAGE_PATH)/modules/$(name)/domain/port/out/$(PascalCaseName)RepositoryPort.java
	@echo ">> Module $(name) created successfully."

## --------------------------------------
## Build
## --------------------------------------

.PHONY: build
build: clean
	@./mvnw package -DskipTests

## --------------------------------------
## Docker
## --------------------------------------

.PHONY: docker-build
docker-build: build
	@echo ">> Building Docker image: $(DOCKER_IMAGE):$(DOCKER_TAG)"
	@docker build -t $(DOCKER_IMAGE):$(DOCKER_TAG) .

.PHONY: docker-run
docker-run:
	@echo ">> Running Docker container from image: $(DOCKER_IMAGE):$(DOCKER_TAG)"
	@docker run --rm -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker" $(DOCKER_IMAGE):$(DOCKER_TAG)

.PHONY: docker-stop
docker-stop:
	@docker ps -q --filter ancestor=$(DOCKER_IMAGE) | xargs -r docker stop

## --------------------------------------
## Help
## --------------------------------------

.PHONY: help
help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

