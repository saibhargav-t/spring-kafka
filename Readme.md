# 🚀 Kafka Tutorial with Spring Boot

## 📑 Table of Contents

- [🚀 Kafka Tutorial with Spring Boot](#-kafka-tutorial-with-spring-boot)
  - [📑 Table of Contents](#-table-of-contents)
  - [✅ Prerequisites](#-prerequisites)
  - [🎯 Getting Started](#-getting-started)
    - [1️⃣ Start MySQL Database](#1️⃣-start-mysql-database)
    - [2️⃣ Start Kafka (WSL / Linux - KRaft Mode)](#2️⃣-start-kafka-wsl--linux---kraft-mode)
    - [3️⃣ Configure Application](#3️⃣-configure-application)
  - [⚙️ Application Properties Explained](#️-application-properties-explained)
    - [4️⃣ Run the Application](#4️⃣-run-the-application)
  - [💡 Usage](#-usage)
    - [📤 Publish a String Message](#-publish-a-string-message)
    - [👤 Publish a User (JSON)](#-publish-a-user-json)
  - [📁 Project Structure](#-project-structure)
  - [🛠️ Technologies Used](#️-technologies-used)

---

This project demonstrates a simple **Spring Boot** application that integrates with **Apache Kafka** for producing and consuming messages. It supports both simple String messages and JSON objects (User data), and persists consumed User data into a **MySQL** database.

## ✅ Prerequisites

- ☕ **Java 17+**
- 📦 **Maven**
- 🐧 **Apache Kafka** (Installed in WSL/Linux, running in KRaft mode)
- 🗄️ **MySQL Server** (Local installation)

## 🎯 Getting Started

### 1️⃣ Start MySQL Database

Ensure you have a MySQL server running locally on port `3306`.

1. Create a database named `kafka_tutorial`.
2. Update `src/main/resources/application.properties` with your MySQL username and password.

### 2️⃣ Start Kafka (WSL / Linux - KRaft Mode)

This project uses Kafka in **KRaft mode** (without Zookeeper). Run these commands inside your WSL terminal or Linux environment.

- 📥 Install Kafka from [Download Kafka](https://kafka.apache.org/quickstart)

1. **🔑 Generate a Cluster UUID**

   Navigate to your Kafka installation directory and run:

   ```bash
   KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
   ```

2. **💾 Format Log Directories**

   ```bash
   bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties
   ```

3. **🌐 Configure Network Access (Important for WSL)**

   To allow your Windows host or other devices to access Kafka running in WSL, you need to modify `config/kraft/server.properties`.

   Open `config/kraft/server.properties` and find the following settings. Uncomment/Edit them to look like this:

   ```properties
   # Allow Kafka to listen on all interfaces (0.0.0.0)
   listeners=PLAINTEXT://0.0.0.0:9092
   # Tell clients (like your Spring Boot app) to connect to localhost:9092
   advertised.listeners=PLAINTEXT://localhost:9092
   ```

4. **▶️ Start Kafka Server**

   Start the Kafka broker:

   ```bash
   bin/kafka-server-start.sh config/server.properties
   ```

### 3️⃣ Configure Application

Ensure `src/main/resources/application.properties` points to your Kafka and MySQL.

## ⚙️ Application Properties Explained

Here is a detailed explanation of the configuration in `src/main/resources/application.properties`:

| Property                                                        | Description                                                                                     |
| :-------------------------------------------------------------- | :---------------------------------------------------------------------------------------------- |
| **🔧 General**                                                   |                                                                                                 |
| `spring.application.name`                                       | The name of the application (`kafka-tutorial`). Used for logging and service discovery.         |
| `server.port`                                                   | The HTTP port the application runs on (`2619`).                                                 |
| **📡 Kafka Common**                                              |                                                                                                 |
| `spring.kafka.bootstrap-servers`                                | Address of the Kafka broker(s). Defaults to `localhost:9092`.                                   |
| **📥 Kafka Consumer**                                            |                                                                                                 |
| `spring.kafka.consumer.bootstrap-servers`                       | Overrides the global bootstrap servers for the consumer if needed.                              |
| `spring.kafka.consumer.group-id`                                | ID of the consumer group (`myGroup`). Consumers with the same ID share the workload.            |
| `spring.kafka.consumer.auto-offset-reset`                       | What to do when no initial offset is found (`earliest`: start from the beginning of the topic). |
| `spring.kafka.consumer.key-deserializer`                        | Class used to deserialize message keys (String).                                                |
| `spring.kafka.consumer.value-deserializer`                      | Class used to deserialize message values (JSON).                                                |
| `spring.kafka.consumer.properties.spring.json.trusted.packages` | Whitelist of packages trusted for JSON deserialization (`*` trusts all).                        |
| **📤 Kafka Producer**                                            |                                                                                                 |
| `spring.kafka.producer.bootstrap-servers`                       | Overrides the global bootstrap servers for the producer if needed.                              |
| `spring.kafka.producer.key-serializer`                          | Class used to serialize message keys (String).                                                  |
| `spring.kafka.producer.value-serializer`                        | Class used to serialize message values (JSON).                                                  |
| **🗄️ Database**                                                  |                                                                                                 |
| `spring.datasource.url`                                         | JDBC URL for connecting to MySQL (`jdbc:mysql://localhost:3306/kafka_tutorial`).                |
| `spring.datasource.username`                                    | Database username.                                                                              |
| `spring.datasource.password`                                    | Database password.                                                                              |
| `spring.jpa.show-sql`                                           | If `true`, prints SQL statements to the console.                                                |
| `spring.jpa.properties.hibernate.format_sql`                    | If `true`, formats the printed SQL for better readability.                                      |
| `spring.jpa.hibernate.ddl-auto`                                 | Automatically updates the database schema (`update`).                                           |
| **📝 Logging**                                                   |                                                                                                 |
| `logging.level.org.hibernate.SQL`                               | Sets logging level for SQL statements to `DEBUG`.                                               |
| `logging.level.org.hibernate.orm.jdbc.bind`                     | Sets logging level for JDBC parameter binding to `TRACE` (shows values in prepared statements). |

### 4️⃣ Run the Application

```bash
mvn spring-boot:run
```

## 💡 Usage

### 📤 Publish a String Message

Send a simple text message to the `tutorial` topic.

**Endpoint:** `GET /api/v1/kafka/publish`

**Example:**

```bash
curl "http://localhost:2619/api/v1/kafka/publish?message=HelloKafka"
```

**✅ Expected Output:**

- Application logs: `Message recieved from broker is: HelloKafka`

### 👤 Publish a User (JSON)

Send a User JSON object to the `userJson` topic. The consumer will receive it and save it to the MySQL database.

**Endpoint:** `POST /api/v1/kafka/user`

**Body:**

```json
{
    "name": "John Doe",
    "city": "New York",
    "age": 30
}
```

**Example:**

```bash
curl -X POST http://localhost:2619/api/v1/kafka/user \
 -H "Content-Type: application/json" \
 -d "{\"name\": \"John Doe\", \"city\": \"New York\", \"age\": 30}"
```

**✅ Expected Output:**

- Application logs: `User details recieved: User(id=..., name=John Doe, city=New York, age=30)`
- Database: A new row inserted into the `user` table.

## 📁 Project Structure

``` text
src/main/java/com/example/kafka/kafka_tutorial/
├── 🎮 controller/
│   └── RestControllers.java       # REST endpoints to trigger producers
├── 📡 kafka/
│   ├── Producer.java              # Sends messages to Kafka topics
│   └── Consumer.java              # Listens to Kafka topics and processes messages
├── 👤 pojo/
│   └── User.java                  # JPA Entity and DTO for User data
└── 💾 dao/
    └── DAO.java                   # Repository for database operations
```

## 🛠️ Technologies Used

- 🍃 **Spring Boot 3.5.7** - Application framework
- 📨 **Spring Kafka** - Kafka integration
- 🗄️ **MySQL** - Database for persistence
- 🔧 **Lombok** - Reduce boilerplate code
- 📦 **Maven** - Dependency management
- ⚡ **Apache Kafka (KRaft)** - Message broker

---

**Made with ❤️ using Spring Boot and Apache Kafka**
