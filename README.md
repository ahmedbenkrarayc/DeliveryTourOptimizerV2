# 🚚 Delivery Tour Optimization System — Version 2  
An AI-Enhanced Delivery Management & Route Optimization Platform

## 📦 Overview
The **Delivery Tour Optimization System — V2** is the upgraded and modernized version of the original application (V1) that helps logistics companies optimize delivery routes and manage clients, vehicles, and operations efficiently.

V1 introduced a complete delivery ecosystem with two optimization algorithms (Nearest Neighbor and Clarke & Wright).  
V2 extends the solution with new intelligent features, new entities, database migrations, environment-based configurations, and advanced search capabilities.

---

# 🎯 Main Features

## 🔹 New Features Introduced in V2

### 🧍 Customer Management
A new `Customer` entity with:
- Full name
- Address
- GPS coordinates
- Preferred delivery time slot

Each customer can have multiple deliveries, enabling personalization and deeper analytics.

---

### 📜 Delivery History Tracking
A new `DeliveryHistory` entity is automatically created whenever a Tour reaches the `COMPLETED` status.

Each history record includes:
- Customer information  
- Tour reference  
- Delivery date  
- Planned & actual delivery times  
- Delay (actualTime - plannedTime)  
- Day of week  
This dataset is used by the AI optimizer.

---

### 🗃️ Liquibase Database Versioning
V2 uses **Liquibase** for clean and reproducible schema management.

Changelog structure:
- `db.changelog-master.xml`
- `db.changelog-v1.0-initial.xml`
- `db.changelog-v2.0-new-entities.xml`
- Additional changelogs by feature

Includes:
- Minimum 5 changeSets
- At least 1 rollback example
- Sync between DEV and QA environments

---

### ⚙️ Multi-Environment YAML Configuration
Replaces `application.properties` with:
- `application.yml`  
- `application-dev.yml` (H2 database)  
- `application-qa.yml` (SQL database)

Environments are cleanly separated and configurable.

---

### 🧵 Annotation-Based Dependency Injection  
V2 completely removes XML dependency injection from V1.

Replaces `applicationContext.xml` with annotation-based DI:
- `@Configuration`
- `@Bean`
- `@Service`, `@Repository`, `@Component`
- `@ConditionalOnProperty` for optimizer selection

---

### 🤖 AI-Based Route Optimization (AIOptimizer)
A third optimizer implementation built using **Spring AI**.

Capabilities:
- Analyzes DeliveryHistory to detect patterns  
- Recommends the most efficient delivery order  
- Outputs structured JSON:
  - Optimized delivery list
  - Recommendations
  - Patterns & insights  

Supports:
- Local LLMs via **Ollama** (TinyLlama recommended)
- Cloud LLMs (OpenAI, HuggingFace, etc.)

---

## 🔹 Features Inherited from Version 1

### 🚗 Vehicle Fleet Management
Includes Vehicle types: BIKE, VAN, TRUCK  
Each with capacity, weight, volume, and max delivery constraints.

### 📍 Delivery Management
Each delivery includes:
- GPS coordinates
- Weight & volume
- Optional preferred time slot
- Status progression: `PENDING → IN_TRANSIT → DELIVERED/FAILED`

### 🏭 Warehouse
Central hub for all tours with fixed opening hours (06:00–22:00).

### 🗺️ Route Optimization Algorithms
Two algorithms from V1 remain:
- **Nearest Neighbor**
- **Clarke & Wright Savings**

### 📊 Algorithm Comparison
V1 logic still available to compare:
- Distance
- Execution time

### 🧱 CRUD Operations and REST API
Full CRUD support for all entities.

### 🧰 API Documentation
Swagger UI available for full documentation.

### 🧪 Testing
Unit testing with JUnit + new V2 integration testing.

---

# 🧮 Algorithms

### 🔹 Nearest Neighbor
Greedy algorithm selecting the nearest unvisited point.
- Ultra fast
- Less optimal

### 🔹 Clarke & Wright
Savings-based merging algorithm.
- Slightly slower
- Reduces total distance by ~28%

### 🔹 AI Optimizer (V2)
Uses historical patterns to generate context-aware optimization.

---

# 🏗️ Project Structure

```
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───deliverytouroptimizer
│   │   │           └───deliverytouroptimizerv2
│   │   │               ├───config
│   │   │               ├───controller
│   │   │               ├───dto
│   │   │               │   ├───request
│   │   │               │   │   ├───customer
│   │   │               │   │   ├───delivery
│   │   │               │   │   ├───deliveryhistory
│   │   │               │   │   ├───tour
│   │   │               │   │   ├───vehicle
│   │   │               │   │   └───warehouse
│   │   │               │   └───response
│   │   │               │       ├───customer
│   │   │               │       ├───delivery
│   │   │               │       ├───deliveryhistory
│   │   │               │       ├───tour
│   │   │               │       ├───vehicle
│   │   │               │       └───warehouse
│   │   │               ├───event
│   │   │               ├───exception
│   │   │               ├───listener
│   │   │               ├───mapper
│   │   │               ├───model
│   │   │               │   └───enums
│   │   │               ├───optimizer
│   │   │               │   └───algorithm
│   │   │               ├───repository
│   │   │               └───service
│   │   │                   ├───impl
│   │   │                   └───validation
│   │   └───resources
│   │       ├───db
│   │       │   └───changelog
│   │       ├───static
│   │       └───templates
│   └───test
│       └───java
│           └───com
│               └───deliverytouroptimizer
│                   └───deliverytouroptimizerv2
│                       ├───integration
│                       │   └───service
│                       │       └───impl
│                       └───service
│                           └───impl
```

---

# 📄 Class Diagram

<img width="1304" height="676" alt="image" src="https://github.com/user-attachments/assets/5f81f16e-be58-451b-bed2-7ee3b9d6f07b" />


---

# 🚀 Getting Started

### 1️⃣ Run in DEV mode (H2)
```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 2️⃣ Run in Test mode
```
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

### 3️⃣ Access Swagger UI
```
http://localhost:8080/swagger-ui.html
```

---

# 🧪 Testing
Run all tests:
```
mvn test
```

---

# 🏁 Version Summary

| Feature | V1 | V2 |
|--------|----|----|
| Customer system | ❌ | ✔️ |
| Delivery history | ❌ | ✔️ |
| Optimizers | NN + CW | NN + CW + AI |
| Database | H2 only | Multi-env + Liquibase |
| Config format | properties | YAML |
| DI | XML | Annotations |
| Search | Basic | Advanced + Pagination |
| Integration Tests | Optional | ✔️ |

---

