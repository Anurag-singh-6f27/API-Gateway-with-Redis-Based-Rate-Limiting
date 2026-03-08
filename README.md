# API Gateway with Redis-Based Rate Limiting

A distributed **microservices backend system** built with **Spring Boot**, demonstrating an **API Gateway architecture**, **inter-service communication**, and **Redis-based rate limiting**.

This project simulates a real backend platform where all client requests pass through an **API Gateway** that handles routing, traffic control, and logging before forwarding them to backend services.

---

## 🚀 Features

* **API Gateway Pattern**

    * Central entry point for all requests
    * Request routing to internal microservices

* **Redis Rate Limiting**

    * Limits requests per API key
    * Prevents abuse and traffic spikes

* **Microservices Architecture**

    * Independent services for different responsibilities
    * Inter-service HTTP communication

* **Automatic Notifications**

    * When a user is created, the system generates a notification automatically

* **Request Logging**

    * API requests logged in PostgreSQL
    * Enables monitoring and analytics

* **Dockerized Infrastructure**

    * Redis container
    * PostgreSQL container

---

## 🏗 Architecture

```
Client
   │
   ▼
API Gateway (Spring Boot)
   │
   ├── API Key Authentication
   ├── Redis Rate Limiting
   ├── Request Logging
   │
   ▼
User Service
   │
   └── calls → Notification Service
                     │
                     ▼
               Notification Database
```

---

## 🧩 Microservices

### 1️⃣ API Gateway

Port: **8080**

Responsibilities:

* Request routing
* Redis rate limiting
* API request logging
* API key validation

Routes:

```
POST /api/users
GET /api/notifications/{userId}
```

---

### 2️⃣ User Service

Port: **8081**

Handles user management.

Endpoints:

```
POST /users
GET /users
GET /users/{id}
```

When a user is created, the service automatically calls the **Notification Service**.

---

### 3️⃣ Notification Service

Port: **8082**

Handles user notifications.

Endpoints:

```
POST /notifications
GET /notifications/{userId}
```

Example notification:

```
User account successfully created
```

---

## 🧠 Rate Limiting Algorithm

Redis stores request counts using keys:

```
rate_limit:{apiKey}
```

Algorithm:

```
1. Increment request count
2. If first request → set TTL = 60 seconds
3. If count > limit → return 429
```

Example response:

```
429 Too Many Requests
```

---

## 🗄 Database

PostgreSQL stores:

### Users

```
id
name
email
created_at
```

### Notifications

```
id
user_id
message
created_at
read
```

### API Request Logs

```
id
api_key
endpoint
method
status_code
timestamp
response_time
```

---

## ⚙️ Tech Stack

* Java
* Spring Boot
* Spring Data JPA
* Redis
* PostgreSQL
* Docker
* Maven

---

## 🐳 Running the Project

### 1️⃣ Start Infrastructure

Start Redis:

```
docker run -d -p 6379:6379 redis
```

Start PostgreSQL:

```
docker run -d -p 5433:5432 \
-e POSTGRES_DB=microservices_db \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
postgres
```

---

### 2️⃣ Run Services

Start each service:

```
api-gateway
user-service
notification-service
```

---

## 📡 Example API Requests

### Create User

```
POST http://localhost:8080/api/users
```

Header:

```
X-API-KEY: client123
```

Body:

```json
{
 "name": "John",
 "email": "john@email.com"
}
```

---

### Get Notifications

```
GET http://localhost:8080/api/notifications/{userId}
```

---

## 🔥 Rate Limiting Demo

Send more than **10 requests per minute** using the same API key.

Expected result:

```
429 Too Many Requests
```

---

## 📊 Future Improvements

* API Key management service
* Distributed tracing
* Kafka-based event notifications
* Spring Cloud Gateway integration
* Prometheus monitoring


