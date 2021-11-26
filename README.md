# Run Kafka

    cd kafka
    docker-compose up -d

# Inspect schema registry

    curl http://localhost:8085/subjects
    curl http://localhost:8085/subjects/some-topic-value/versions
    curl http://localhost:8085/subjects/some-topic-value/versions/1

    # compatibility
    curl http://localhost:8085/config/
    curl http://localhost:8085/config/some-topic-value

    curl -X PUT -H "Content-Type: application/json" -d '{"compatibility": "FULL"}' http://localhost:8085/config
