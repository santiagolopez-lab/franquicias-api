# 🏢 Franquicias API

> **Enterprise-grade Franchise Management System** built with **Clean Architecture**, **Spring WebFlux** and **Reactive Programming** principles.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-yellow.svg)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## 🌟 Project Overview

A **production-ready** RESTful API for managing franchises, branches, and products with advanced features like reactive programming, comprehensive validation, and cloud deployment readiness.

### 🎯 Key Features

- **🔄 Fully Reactive**: Non-blocking I/O with WebFlux and R2DBC
- **🏗️ Clean Architecture**: Hexagonal architecture with clear separation of concerns
- **📊 MySQL Integration**: Reactive database access with connection pooling
- **🐳 Docker Ready**: Complete containerization with multi-stage builds
- **📚 API Documentation**: Interactive Swagger UI with OpenAPI 3.0
- **☁️ Cloud Ready**: Environment-based configuration for any cloud provider
- **🔒 Production Grade**: Comprehensive error handling and validation
- **📈 Monitoring**: Health checks and metrics endpoints

## 🚀 Quick Start

### 🐳 Docker Deployment (Recommended)

```bash
# Single command deployment
docker-compose -f docker-compose.new.yml up --build -d

# Verify deployment
docker ps

# Test API
curl http://localhost:8080/api/v1/franchises

# Access Swagger UI
open http://localhost:8080/swagger-ui.html

# Access phpMyAdmin (Database Management)
open http://localhost:8081
```

### 🛠️ Local Development

```bash
# Prerequisites: Java 21, Docker (for MySQL)

# 1. Start MySQL with Docker
docker run -d --name mysql-dev \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=franquicias_db \
  -e MYSQL_USER=franquicias_user \
  -e MYSQL_PASSWORD=franquicias_password \
  -p 3306:3306 mysql:8.0

# 2. Set Environment Variables
export SPRING_PROFILES_ACTIVE=dev
export DB_HOST=localhost
export DB_USERNAME=franquicias_user
export DB_PASSWORD=franquicias_password

# 3. Run from IDE (Recommended)
# Open in IntelliJ IDEA/VS Code and run FranquiciasApiApplication.main()
```

## 📋 Requirements Implementation Status

### ✅ Core Requirements (100% Implemented & Tested)

| Requirement | Endpoint | Status | Description |
|-------------|----------|--------|-------------|
| **Add Franchise** | `POST /api/v1/franchises` | ✅ | Create new franchise with validation |
| **Add Branch** | `POST /api/v1/franchises/{id}/branches` | ✅ | Add branch to specific franchise |
| **Add Product** | `POST /api/v1/branches/{id}/products` | ✅ | Add product to specific branch |
| **Remove Product** | `DELETE /api/v1/products/{id}` | ✅ | Delete product with proper error handling |
| **Update Stock** | `PUT /api/v1/products/{id}/stock` | ✅ | Modify product stock levels |
| **Top Stock Report** | `GET /api/v1/franchises/{id}/top-stock-products` | ✅ | Get highest stock product per branch |

### ✅ Extra Requirements (100% Implemented & Tested)

| Feature | Endpoint | Status | Description |
|---------|----------|--------|-------------|
| **Update Franchise Name** | `PUT /api/v1/franchises/{id}/name` | ✅ | Modify franchise name with validation |
| **Update Branch Name** | `PUT /api/v1/branches/{id}/name` | ✅ | Modify branch name with constraints |
| **Update Product Name** | `PUT /api/v1/products/{id}/name` | ✅ | Modify product name |
| **Get All Franchises** | `GET /api/v1/franchises` | ✅ | List all franchises with branches |

### ✅ Technical Requirements (100% Implemented)

| Requirement | Implementation | Status | Details |
|-------------|----------------|--------|---------|
| **Spring WebFlux** | RouterFunctions + Handlers | ✅ | Fully reactive, non-blocking I/O |
| **Clean Architecture** | 5-module Gradle project | ✅ | Domain, UseCase, Infrastructure layers |
| **MySQL Persistence** | R2DBC + Flyway | ✅ | Reactive database access with migrations |
| **OpenAPI Documentation** | SpringDoc + Swagger UI | ✅ | Interactive API documentation |
| **Cloud Deployment** | Docker + Environment Config | ✅ | Ready for AWS/GCP/Azure deployment |

### ⚠️ Infrastructure as Code Status

| Component | Status | Notes |
|-----------|--------|-------|
| **Application Architecture** | ✅ Complete | Cloud-ready configuration |
| **Docker Configuration** | ✅ Complete | Multi-stage builds optimized |
| **Environment Variables** | ✅ Complete | Supports any cloud provider |
| **Terraform Scripts** | ⚠️ Not Implemented | Application is Terraform-ready |

## 🏗️ Technical Architecture

### 📐 Clean Architecture Implementation

```
franquicias-api/
├── 🎯 domain/               # Business Logic (Core)
│   ├── entities/           # Enterprise Business Rules
│   ├── repositories/       # Repository Interfaces
│   └── exceptions/         # Domain Exceptions
├── 🔧 application/          # Use Cases Layer
│   ├── usecases/          # Application Business Rules
│   ├── handlers/          # Request/Response Handlers
│   └── dtos/              # Data Transfer Objects
├── 🌐 infrastructure/       # External Interfaces
│   ├── database/          # R2DBC Repositories
│   ├── config/            # Configuration Classes
│   └── migrations/        # Flyway SQL Scripts
├── 🖥️ web/                 # Web Layer
│   ├── routers/           # RouterFunction Definitions
│   ├── handlers/          # WebFlux Handlers
│   └── config/            # Web Configuration
└── 🚀 app-service/         # Main Application
    ├── main/              # Application Entry Point
    └── resources/         # Configuration Files
```

### 🔄 Reactive Programming Stack

- **Spring WebFlux 6.1.0**: Non-blocking reactive web framework
- **R2DBC MySQL**: Reactive database connectivity
- **Project Reactor**: Mono/Flux reactive streams
- **RouterFunctions**: Functional routing instead of annotations

### 📊 Database Schema

```sql
-- Core Tables (Auto-created via Flyway)
franchises (id, name, created_at, updated_at)
branches (id, franchise_id, name, created_at, updated_at)
products (id, branch_id, name, stock, created_at, updated_at)
sample_data (id, description, created_at)
```

## 🔌 API Reference

### 🏢 Franchise Management

<details>
<summary><strong>GET /api/v1/franchises</strong> - List All Franchises</summary>

```bash
curl -X GET "http://localhost:8080/api/v1/franchises" \
  -H "accept: application/json"
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Nequi Franquicia Central",
    "branches": [
      {
        "id": 1,
        "name": "Sucursal Norte",
        "products": []
      }
    ]
  }
]
```
</details>

<details>
<summary><strong>POST /api/v1/franchises</strong> - Create New Franchise</summary>

```bash
curl -X POST "http://localhost:8080/api/v1/franchises" \
  -H "Content-Type: application/json" \
  -d '{"name": "Mi Nueva Franquicia"}'
```

**Response:**
```json
{
  "id": 3,
  "name": "Mi Nueva Franquicia",
  "branches": []
}
```
</details>

<details>
<summary><strong>PUT /api/v1/franchises/{id}/name</strong> - Update Franchise Name</summary>

```bash
curl -X PUT "http://localhost:8080/api/v1/franchises/1/name" \
  -H "Content-Type: application/json" \
  -d '{"name": "Nombre Actualizado"}'
```

**Response:**
```json
{
  "id": 1,
  "name": "Nombre Actualizado",
  "branches": [...]
}
```
</details>

### 🏪 Branch Management

<details>
<summary><strong>POST /api/v1/franchises/{franchiseId}/branches</strong> - Add Branch to Franchise</summary>

```bash
curl -X POST "http://localhost:8080/api/v1/franchises/1/branches" \
  -H "Content-Type: application/json" \
  -d '{"name": "Nueva Sucursal"}'
```

**Response:**
```json
{
  "id": 3,
  "name": "Nueva Sucursal",
  "products": []
}
```
</details>

### 📦 Product Management

<details>
<summary><strong>POST /api/v1/branches/{branchId}/products</strong> - Add Product to Branch</summary>

```bash
curl -X POST "http://localhost:8080/api/v1/branches/1/products" \
  -H "Content-Type: application/json" \
  -d '{"name": "Producto Nuevo", "stock": 100}'
```

**Response:**
```json
{
  "id": 4,
  "name": "Producto Nuevo",
  "stock": 100
}
```
</details>

<details>
<summary><strong>PUT /api/v1/products/{id}/stock</strong> - Update Product Stock</summary>

```bash
curl -X PUT "http://localhost:8080/api/v1/products/1/stock" \
  -H "Content-Type: application/json" \
  -d '{"stock": 75}'
```

**Response:**
```json
{
  "id": 1,
  "name": "Producto A",
  "stock": 75
}
```
</details>

<details>
<summary><strong>DELETE /api/v1/products/{id}</strong> - Remove Product</summary>

```bash
curl -X DELETE "http://localhost:8080/api/v1/products/1"
```

**Response:** `204 No Content`
</details>

### 📊 Reports & Analytics

<details>
<summary><strong>GET /api/v1/franchises/{id}/top-stock-products</strong> - Top Stock Products Report</summary>

```bash
curl -X GET "http://localhost:8080/api/v1/franchises/1/top-stock-products"
```

**Response:**
```json
[
  {
    "branchId": 1,
    "branchName": "Sucursal Norte",
    "product": {
      "id": 2,
      "name": "Producto B",
      "stock": 150
    }
  },
  {
    "branchId": 2,
    "branchName": "Sucursal Sur",
    "product": {
      "id": 5,
      "name": "Producto E",
      "stock": 200
    }
  }
]
```
</details>

## 🛠️ Development

### 🔧 Prerequisites

- **Java 21** (OpenJDK or Oracle JDK)
- **Docker & Docker Compose** (for MySQL and deployment)
- **IDE**: IntelliJ IDEA (recommended) or VS Code with Java extensions
- **Git** for version control

### 🏃‍♂️ Running Tests

```bash
# Run unit tests
./gradlew test

# Run integration tests
./gradlew integrationTest

# Generate test reports
./gradlew jacocoTestReport
```

### 🔍 Code Quality

```bash
# Check code style
./gradlew checkstyleMain

# Run static analysis
./gradlew spotbugsMain

# Generate reports
./gradlew build
```

### 📊 Monitoring & Health Checks

```bash
# Application health
curl http://localhost:8080/actuator/health

# Application info
curl http://localhost:8080/actuator/info

# Metrics
curl http://localhost:8080/actuator/metrics
```

## 🐳 Docker Configuration

### 📦 Multi-Stage Build

The application uses an optimized Docker build process:

1. **Build Stage**: Gradle build with full JDK
2. **Runtime Stage**: Lightweight Alpine with JRE 21
3. **Health Checks**: Built-in readiness and liveness probes
4. **Security**: Non-root user execution

### 🔧 Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_PROFILES_ACTIVE` | `prod` | Spring profile to activate |
| `DB_HOST` | `mysql` | MySQL database host |
| `DB_PORT` | `3306` | MySQL database port |
| `DB_NAME` | `franquicias_db` | Database name |
| `DB_USERNAME` | `franquicias_user` | Database username |
| `DB_PASSWORD` | `franquicias_password` | Database password |
| `SERVER_PORT` | `8080` | Application port |

## ☁️ Cloud Deployment

### 🎯 Ready for Any Cloud Provider

The application is designed to be cloud-native and can be deployed on:

- **AWS**: ECS, EKS, or Elastic Beanstalk
- **Google Cloud**: Cloud Run, GKE, or App Engine
- **Azure**: Container Instances, AKS, or App Service
- **Heroku**: Direct Docker deployment

### 🔐 Production Considerations

- **Environment Variables**: Externalized configuration
- **Health Checks**: Kubernetes-ready probes
- **Logging**: Structured JSON logging
- **Metrics**: Micrometer/Prometheus integration
- **Security**: HTTPS, CORS, and authentication ready

## 🎯 Future Enhancements

### 🚧 Planned Improvements

- [ ] **Infrastructure as Code**: Terraform modules for AWS/GCP/Azure
- [ ] **Authentication**: JWT or OAuth2 integration
- [ ] **Caching**: Redis integration for performance
- [ ] **Message Queues**: RabbitMQ or Kafka for async processing
- [ ] **API Versioning**: Strategy for backward compatibility
- [ ] **Rate Limiting**: Protection against abuse
- [ ] **Advanced Monitoring**: APM integration (New Relic, DataDog)

### 📈 Performance Optimizations

- [ ] **Connection Pooling**: Optimized R2DBC connection pools
- [ ] **Database Indexing**: Query performance optimization
- [ ] **CDN Integration**: Static asset delivery
- [ ] **Horizontal Scaling**: Load balancer configuration

## 🤝 Contributing

### 🔄 Development Workflow

1. **Fork** the repository
2. **Create** feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** changes (`git commit -m 'Add amazing feature'`)
4. **Push** to branch (`git push origin feature/amazing-feature`)
5. **Open** Pull Request

### 📝 Code Standards

- **Clean Code**: SOLID principles and clean architecture
- **Testing**: Unit and integration test coverage > 80%
- **Documentation**: JavaDoc for public APIs
- **Formatting**: Google Java Style Guide

## 📜 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Spring Team** for the excellent reactive framework
- **Clean Architecture** principles by Robert C. Martin
- **Nequi** for the technical challenge opportunity

---

<div align="center">

**Built with ❤️ for Nequi Technical Assessment**

[📚 API Documentation](http://localhost:8080/swagger-ui.html) | 
[🐳 Docker Hub](https://hub.docker.com/) | 
[📊 Database Admin](http://localhost:8081)

</div>
2. **✅ Add new branch to franchise** - `POST /api/franchises/{franchiseId}/branches`
3. **✅ Add new product to branch** - `POST /api/branches/{branchId}/products`
4. **✅ Delete product from branch** - `DELETE /api/branches/{branchId}/products/{productId}`
5. **✅ Update product stock** - `PUT /api/products/{productId}/stock`
6. **✅ Get product with highest stock per branch** - `GET /api/franchises/{franchiseId}/top-stock-products`

### 🌟 Extra Points (100% Complete)
7. **✅ Update franchise name** - `PUT /api/franchises/{franchiseId}/name`
8. **✅ Update branch name** - `PUT /api/branches/{branchId}/name`

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    CLEAN ARCHITECTURE                       │
├─────────────────────────────────────────────────────────────┤
│  🌐 Presentation Layer (:reactive-web)                     │
│     ├── RouterFunctions (Not @RestController)              │
│     ├── Handlers (Reactive Request Processing)             │
│     ├── DTOs with Validation                               │
│     └── OpenAPI Documentation                              │
├─────────────────────────────────────────────────────────────┤
│  💼 Use Case Layer (:usecase)                              │
│     ├── 9 Use Cases (All Requirements + Extra)             │
│     ├── Business Logic Implementation                      │
│     ├── Reactive Validation                                │
│     └── Error Handling                                     │
├─────────────────────────────────────────────────────────────┤
│  🏛️ Domain Layer (:model)                                   │
│     ├── Entities (Franchise, Branch, Product)              │
│     ├── Repository Ports                                   │
│     ├── Domain Exceptions                                  │
│     └── Business Rules                                     │
├─────────────────────────────────────────────────────────────┤
│  🔌 Infrastructure Layer (:jpa-repository)                 │
│     ├── R2DBC Adapters                                     │
│     ├── JPA Entities                                       │
│     ├── Flyway Migrations                                  │
│     └── Connection Pooling                                 │
├─────────────────────────────────────────────────────────────┤
│  ⚙️ Application Layer (:app-service)                       │
│     ├── Dependency Injection                               │
│     ├── Configuration Management                           │
│     ├── Environment Profiles                               │
│     └── Main Application                                   │
└─────────────────────────────────────────────────────────────┘
```

## 🔧 Technical Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Framework** | Spring WebFlux | 6.1.0 |
| **Architecture** | Clean Architecture | Bancolombia Style |
| **Language** | Java | 21 (LTS) |
| **Build Tool** | Gradle | 8.5 |
| **Database** | MySQL | 8.0 |
| **DB Driver** | R2DBC MySQL | Reactive |
| **Migration** | Flyway | 9.22.3 |
| **Documentation** | OpenAPI 3.0 | SpringDoc |
| **Validation** | Jakarta Bean Validation | 3.0 |
| **Testing** | JUnit 5 + Testcontainers | Latest |

## 📊 API Documentation

### Interactive Documentation
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs

### Sample API Calls

#### 1. Create Franchise
```bash
curl -X POST http://localhost:8080/api/franchises \
  -H "Content-Type: application/json" \
  -d '{"name": "McDonald'\''s Colombia"}'
```

#### 2. Create Branch
```bash
curl -X POST http://localhost:8080/api/franchises/1/branches \
  -H "Content-Type: application/json" \
  -d '{"name": "Zona Rosa"}'
```

#### 3. Add Product
```bash
curl -X POST http://localhost:8080/api/branches/1/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Big Mac", "stock": 50}'
```

#### 4. Update Stock
```bash
curl -X PUT http://localhost:8080/api/products/1/stock \
  -H "Content-Type: application/json" \
  -d '{"stock": 75}'
```

#### 5. Get Top Stock Products
```bash
curl http://localhost:8080/api/franchises/1/top-stock-products
```

#### 6. Update Franchise Name (Extra Points)
```bash
curl -X PUT http://localhost:8080/api/franchises/1/name \
  -H "Content-Type: application/json" \
  -d '{"name": "McDonald'\''s Premium"}'
```

## 🗄️ Database Schema

```sql
-- Auto-generated by Flyway migrations
CREATE TABLE franchises (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

CREATE TABLE branches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    franchise_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (franchise_id) REFERENCES franchises(id),
    UNIQUE KEY unique_branch_per_franchise (franchise_id, name)
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    branch_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (branch_id) REFERENCES branches(id),
    UNIQUE KEY unique_product_per_branch (branch_id, name),
    CHECK (stock >= 0)
);
```

## 🧪 Testing Strategy

### Unit Tests
```bash
# Run all unit tests
./gradlew test

# Run specific module tests
./gradlew :usecase:test
./gradlew :reactive-web:test
```

### Integration Tests
```bash
# Run with Testcontainers (MySQL)
./gradlew integrationTest
```

### Manual Testing
```bash
# Health check
curl http://localhost:8080/actuator/health

# API validation
curl http://localhost:8080/api/franchises
```

## 🚀 Deployment

### AWS Deployment (Terraform Ready)
```bash
# Infrastructure as Code setup
cd terraform/
terraform init
terraform plan
terraform apply
```

### Environment Variables
```bash
# Production Environment
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=your-rds-endpoint.amazonaws.com
export DB_PORT=3306
export DB_NAME=franquicias_db
export DB_USERNAME=your-db-user
export DB_PASSWORD=your-db-password
```

## ⚡ Performance Features

- **Reactive Programming**: Non-blocking I/O throughout
- **Connection Pooling**: R2DBC connection management
- **Optimistic Locking**: Concurrent access handling
- **Database Indexing**: Optimized query performance
- **Caching**: Ready for Redis integration

## 🔒 Security Features

- **Input Validation**: Jakarta Bean Validation
- **SQL Injection Prevention**: Parameterized queries
- **CORS Configuration**: Cross-origin request handling
- **Error Handling**: Secure error responses
- **Health Checks**: Monitoring endpoints

## 📈 Monitoring & Observability

- **Actuator Endpoints**: /actuator/health, /actuator/metrics
- **Structured Logging**: JSON format ready
- **Health Checks**: Database connectivity validation
- **Metrics**: Ready for Prometheus integration

## 🤝 Contributing

This project demonstrates enterprise-level Java development practices:

1. **Clean Architecture** implementation
2. **Reactive Programming** with Spring WebFlux
3. **Test-Driven Development** approach
4. **Production-Ready** configuration
5. **Cloud-Native** design patterns

## 📝 License

This project is created for job application demonstration purposes.

---

**Created for Nequi - Job Application Test**  
**Demonstrating Senior Java Developer Capabilities**
