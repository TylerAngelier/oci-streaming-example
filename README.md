# OCI Streaming

Simple streaming service to show the capability of the Spring Boot Kafka APIs and
the Oracle Streaming Service Kafka compliant APIs.

# How to run

## Locally

All the required dependencies are put into a docker-compose file.
- zookeeper
- kafka
- oracle database

```bash
docker-compose up -d
```

## With Oracle Streaming Service

TODO

## Run and smoke test

Start up the service.

```bash
mvn spring-boot:run
```

Publish a message:
```bash
curl -X POST -F 'message=Hello from localhost!' http://localhost:8080/kafka/publish
```

This will hit a REST endpoint which will call a Spring Boot Service that will save the record and
produce the message to a Kafka topic. An intended bug is left in the `SampleProducerService` code
causing it to produce the same message twice.

Now we can observe how our consumer can remain idempotent as only one of the duplicated messages is processed
by each consumer. There are two consumers so there only be two messages saved and four messages delivered.
Checkout the `SampleConsumerService` to see the implementation of an inbox.

```bash
curl http://localhost:8080/kafka/consumed
```

## Stop Services

```bash
docker-compose down
```