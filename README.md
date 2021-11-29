# Run Demo projects using different schema versions

## Start kafka broker

    cd kafka
    docker-compose up -d

## Send and receive messages

Start two terminal sessions and run both demo apps

Session 1

    cd kafka-demo-app-v1
    ./gradlew bootRun

Session 2

    cd kafka-demo-app-v2
    ./gradlew bootRun

# Inspect schema registry and set compatibility mode

    curl http://localhost:8085/subjects
    curl http://localhost:8085/subjects/some-topic-value/versions
    curl http://localhost:8085/subjects/some-topic-value/versions/1

    # compatibility
    curl http://localhost:8085/config/
    curl http://localhost:8085/config/some-topic-value

    curl -X PUT -H "Content-Type: application/json" -d '{"compatibility": "FULL"}' http://localhost:8085/config


# Generate java classes with maven instead of gradle

    cd avro-sourcegen-maven
    ./mvnw clean generate-sources
