# Project 3: Microservices Blog Platform

A microservices-based blog platform built with Spring Boot, Spring Cloud, Kafka, and Docker, featuring distributed tracing with Zipkin and custom request logging. This project demonstrates core microservices concepts, including service discovery, API gateways, asynchronous communication, and observability.

## Project Overview
This project transforms a monolithic blog API into a microservices architecture, introducing distributed systems concepts. It comprises three services:
- **user-service**: Manages user creation and storage.
- **post-service**: Handles post creation and retrieval with asynchronous processing.
- **api-gateway**: Routes requests to appropriate services using service discovery.

The platform leverages Kafka for event-driven communication, Eureka for service discovery, and Zipkin for distributed tracing. All services are containerized using Docker Compose.

## Architecture
- **user-service**: REST API (`POST /api/users`) to create users, persists to MySQL, publishes Kafka events.
- **post-service**: REST API (`GET /api/posts`, `POST /api/posts`), stores posts in MySQL, uses caching and a multi-threaded `TaskScheduler` for async post creation.
- **api-gateway**: Spring Cloud Gateway for routing requests, integrated with Eureka.
- **Supporting Services**:
   - MySQL: Persistent storage for users and posts.
   - Kafka: Asynchronous event handling (e.g., post creation events).
   - Zookeeper: Kafka dependency for coordination.
   - Eureka: Service discovery for dynamic routing.
   - Zipkin: Distributed tracing for request tracking.

## Technologies
- **Java 17**: Core programming language.
- **Spring Boot 3.x**: Microservices framework.
- **Spring Cloud**: Gateway, Eureka discovery, and tracing.
- **Spring Data JPA**: ORM for MySQL.
- **Spring Kafka**: Event-driven communication.
- **Docker & Docker Compose**: Containerization.
- **MySQL**: Relational database.
- **Kafka & Zookeeper**: Message queue and coordination.
- **Zipkin**: Distributed tracing.
- **Micrometer Tracing & Brave**: Trace/span ID generation and MDC integration.
- **Maven**: Dependency management.

## Setup Instructions
1. **Prerequisites**:
   - Java 17+
   - Maven 3.8+
   - Docker & Docker Compose
   - Git

2. **Clone the Repository**:
```bash
 git clone <repository-url>
```

3. **Build the Project**:
```bash
mvn clean package
```
4. **Run with Docker Compose**:
```bash
docker-compose up --build
```
5. **Access Services**:

    - API Gateway:  **http://localhost:8080**
  
    - Zipkin UI:  **http://localhost:9411**
  
    - Eureka Dashboard:  **http://localhost:8761**

6. **Test Endpoints**:
```bash
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"username":"Alice"}'
curl -X POST http://localhost:8080/api/posts -H "Content-Type: application/json" -d '{"title":"New Post","content":"Content","author":"Alice"}'
curl http://localhost:8080/api/posts
```
7. **View Logs**:
```bash
docker-compose logs user-service
docker-compose logs post-service
docker-compose logs api-gateway
```

8. **Shuting down Services**:
```bash
docker-compose down
``` 


