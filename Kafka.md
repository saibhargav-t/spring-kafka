# Kafka

## Table of Contents

- [Kafka](#kafka)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
    - [Short History](#short-history)
    - [Kafka in Microservices Architecture](#kafka-in-microservices-architecture)
    - [Use Cases](#use-cases)
  - [Components of Kafka](#components-of-kafka)
    - [Topics](#topics)
      - [Partitioning and Offsets](#partitioning-and-offsets)
    - [Brokers](#brokers)
    - [Clusters](#clusters)
    - [Producers](#producers)
    - [Consumers](#consumers)
    - [Zoo Keeper](#zoo-keeper)
  - [Installation and Steps](#installation-and-steps)

## Introduction

Apache Kafka is a distributed streaming platform that can publish, subscribe to, store, and process streams of records in a fault-tolerant way. It was originally developed at LinkedIn and later open-sourced in 2011. Kafka is designed to handle high-throughput, low-latency data feeds and is often used for building real-time data pipelines and streaming applications.

### Short History

Kafka was initially conceived at LinkedIn to address the need for a highly scalable and reliable system to handle the company's massive volume of event data. Before Kafka, LinkedIn used a variety of custom-built systems, which were difficult to maintain and scale. Jay Kreps, Neha Narkhede, and Jun Rao were the primary creators of Kafka. It was open-sourced in 2011 and quickly gained popularity due to its robust performance and flexibility. In 2014, the creators of Kafka founded Confluent, a company focused on building commercial products and services around Kafka.

### Kafka in Microservices Architecture

In a microservices architecture, applications are broken down into smaller, independent services that communicate with each other. Kafka plays a crucial role in this paradigm by providing a reliable and scalable messaging backbone.

Here's how Kafka is typically used in microservices:

- **Asynchronous Communication:** Microservices can communicate asynchronously by publishing events to Kafka topics and subscribing to events from other services. This decouples services, allowing them to operate independently and reducing direct dependencies.
- **Event Sourcing:** Kafka can be used to implement event sourcing, where all changes to application state are stored as a sequence of immutable events. This provides an audit trail and allows for easy reconstruction of application state.
- **Data Integration:** Kafka acts as a central hub for integrating data across various microservices and external systems. Services can publish their data changes to Kafka, and other services can consume these changes to update their own data stores or trigger business logic.
- **Real-time Data Pipelines:** Kafka enables the creation of real-time data pipelines, where data flows continuously between services, allowing for immediate processing and analysis.
- **Load Balancing and Scalability:** Kafka's distributed nature allows for easy scaling of message processing. Multiple instances of a microservice can consume messages from the same topic, distributing the load and improving throughput.

### Use Cases

Kafka's versatility makes it suitable for a wide range of use cases:

- **Activity Tracking:** Tracking user activity on websites or applications (e.g., page views, clicks, searches) for real-time monitoring, personalization, and analytics.
- **Messaging System:** Acting as a robust message broker for inter-application communication, replacing traditional message queues.
- **Log Aggregation:** Collecting and centralizing logs from various services and applications for monitoring, analysis, and auditing.
- **Stream Processing:** Processing data streams in real-time using Kafka Streams API or other stream processing frameworks (e.g., Apache Flink, Apache Spark Streaming) for immediate insights and actions.
- **Commit Log for Distributed Systems:** Serving as a distributed commit log to ensure data consistency and durability across distributed systems.
- **IoT Data Ingestion:** Ingesting and processing high volumes of data from IoT devices for real-time analytics and anomaly detection.

---

## Components of Kafka

Kafka's architecture is composed of several key components that work together to provide its robust and scalable streaming capabilities.

### Topics

Topics are categories or feeds to which messages are published. They are the fundamental unit of organization in Kafka.

- **Logical Grouping:** Topics logically group related messages. For example, a topic named `user_events` might contain all messages related to user interactions on a website.

#### Partitioning and Offsets

Kafka ensures that messages within a partition are ordered. Each message in a partition is assigned a unique, sequential identifier called an "offset."

- **Ordering:** Messages within a partition are strictly ordered by their offsets. This means that if a producer sends messages M1, M2, and M3 to a partition, consumers will always read them in the order M1, M2, M3.
- **Immutability:** Once a message is written to a partition, it cannot be changed or deleted. It remains in the partition for a configurable period (retention policy) or until storage limits are reached.
- **Parallelism:** Partitions are the unit of parallelism in Kafka. Multiple consumers can read from different partitions of the same topic concurrently, increasing throughput.
- **Distribution:** Partitions are distributed across different brokers in a Kafka cluster. This allows Kafka to scale horizontally, as more brokers can be added to host more partitions.
- **Consumer Offsets:** Consumers track their progress in a partition using offsets. When a consumer reads a message, it updates its offset to the next message it expects to read. This allows consumers to stop and restart without losing their place.

### Brokers

Kafka brokers are the core components of the Kafka cluster. Each broker is a server that stores data (messages) in topics and handles requests from producers and consumers. A Kafka cluster typically consists of multiple brokers to ensure high availability and fault tolerance.

- **Storage:** Brokers store messages on disk, allowing for durable storage even if a broker fails.
- **Replication:** Topics can be replicated across multiple brokers, meaning that copies of the data exist on different servers. This ensures that data is not lost if one broker goes down.
- **Partitioning:** Topics are divided into partitions, and each partition is an ordered, immutable sequence of messages. Partitions are distributed across brokers, enabling parallel processing and scalability.

### Clusters

A Kafka cluster is a distributed system consisting of one or more Kafka brokers. These brokers work together to store and manage messages, providing high availability and fault tolerance. A minimum of 3 brokers for a cluster.

- **Distributed Architecture:** Messages are distributed across multiple brokers and partitions, allowing for horizontal scalability and parallel processing.
- **High Availability:** If one broker fails, other brokers in the cluster can take over its responsibilities, ensuring continuous operation and data availability.
- **Scalability:** As data volume grows, new brokers can be added to the cluster to increase storage capacity and processing power.
- **Fault Tolerance:** Data is replicated across multiple brokers, so even if some brokers fail, data is not lost and remains accessible.

### Producers

Producers are client applications that publish (write) messages to Kafka topics. They are responsible for choosing which topic and partition to send messages to.

- **Message Sending:** Producers send messages to a specific topic. They can optionally specify a key for the message, which Kafka uses to ensure that all messages with the same key go to the same partition, maintaining order.
- **Load Balancing:** Producers can distribute messages across different partitions of a topic, helping to balance the load on the brokers.

### Consumers

Consumers are client applications that subscribe to (read) messages from Kafka topics. They read messages from one or more partitions in a topic.

- **Consumer Groups:** Consumers typically operate within consumer groups. Each message in a partition is delivered to only one consumer instance within a consumer group. This allows for parallel processing of messages and scaling of consumption.
- **Offset Tracking:** Consumers keep track of their "offset," which is the position of the last message they have read in a partition. This allows them to resume reading from where they left off if they stop and restart.
- **Fault Tolerance:** If a consumer instance fails, other consumers in the same group can take over its partitions, ensuring continuous processing.

### Zoo Keeper

Apache ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services. It is a crucial component in a Kafka ecosystem, though its role is diminishing in newer Kafka versions.

Here's what ZooKeeper does for Kafka:

- **Controller Election:** In a Kafka cluster, one broker is elected as the "controller." The controller is responsible for administrative operations like assigning partitions to brokers and recovering from broker failures. ZooKeeper facilitates this election process.
- **Cluster Membership:** ZooKeeper keeps track of all active brokers in the Kafka cluster. When a broker joins or leaves the cluster, ZooKeeper is updated, and this information is propagated to other brokers.
- **Topic Configuration:** Information about topics, such as the number of partitions, replication factor, and their configuration, is stored in ZooKeeper.
- **Access Control Lists (ACLs):** If ACLs are enabled for security, ZooKeeper stores the permissions for users and applications to access Kafka topics.
- **Consumer Offsets (Older Kafka Versions):** In older versions of Kafka (prior to 0.9), consumer offsets (the position of the last message read by a consumer) were stored in ZooKeeper. Newer versions store offsets directly in Kafka topics, reducing ZooKeeper's dependency.

**Why its role is diminishing:**

Kafka is moving towards removing its dependency on ZooKeeper. This effort, known as "KRaft" (Kafka Raft Metadata mode), aims to replace ZooKeeper with a new metadata management system built directly into Kafka using the Raft consensus algorithm. This will simplify Kafka deployments, reduce operational overhead, and improve scalability.

---

## Installation and Steps

- Install from [Kafka](https://kafka.apache.org/quickstart), if you are using linux follow procedure.
- If you are on windows you need to install Ubuntu in WSL and configure kafka in ubuntu.
- You need to make sure that server is discoverable outside of WSL by editing the /config/server.properties file. The canges you need to make is `listeners=PLAINTEXT://0.0.0.0:9092` and `advertised.listeners=PLAINTEXT://localhost:9092`.
- Test if it is running or not with this `advertised.listeners=PLAINTEXT://localhost:9092`. If TcpTestSucceeded : True then it is working.
-To start the kafka server we have to use `bin/kafka-server-start.sh config/server.properties`.
- For creating kafka topics, `bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1`
- For describing existing topic, `bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092`.
