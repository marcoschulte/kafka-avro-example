# Run Kafka

    cd kafka
    docker-compose up -d

# Inspect schema registry

    curl http://localhost:8085/subjects
    curl http://localhost:8085/subjects/some-topic-value/versions
    curl http://localhost:8085/subjects/some-topic-value/versions/1
