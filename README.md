# Alert Management Microservice

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![GraphQL](https://img.shields.io/badge/GraphQL-Enabled-e10098.svg)](https://graphql.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)

A robust microservice for managing alert configurations in the FleetGuard360 ecosystem. This service provides comprehensive CRUD operations for alert types, priority levels, and alert configurations through a GraphQL API.

## 🏗️ Architecture Overview

This microservice follows **Clean Architecture** principles with clear separation of concerns:

```
src/main/java/com/fleetguard360/alert_management/
├── presentation/          # API layer (Controllers, DTOs, Exception Handlers)
│   ├── controller/        # GraphQL Controllers
│   ├── DTO/              # Data Transfer Objects (organized by domain)
│   │   ├── tipoalerta/   # Alert Type DTOs
│   │   ├── nivelprioridad/ # Priority Level DTOs
│   │   └── configuracionalerta/ # Alert Configuration DTOs
│   └── advice/           # Global Exception Handler
├── service/              # Business Logic layer
│   ├── interfaces/       # Service Contracts
│   ├── implementation/   # Service Implementations
│   └── exception/        # Custom Business Exceptions
├── persistence/          # Data Access layer
│   ├── entity/          # JPA Entities
│   └── repository/      # JPA Repositories
└── configuration/        # Configuration layer
    └── mapper/          # MapStruct Mappers
```

## 🚀 Features

### Core Entities

#### 1. **TipoAlerta (Alert Type)**
- Catalog of available alert types with comprehensive information
- **New Features**:
  - **Priority Level Integration**: Each alert type has an associated priority level
  - **Responsible Type**: Defines who should handle the alert (conductor, mechanic, technical support, logistics operator, security)
  - Activation/deactivation capability
  - Unique name validation

#### 2. **NivelPrioridad (Priority Level)**
- Simplified priority levels for alerts
- **Streamlined Design**: Removed color coding for simpler management
- Unique name validation

#### 3. **ConfiguracionAlerta (Alert Configuration)**
- **Simplified Model**: Links alert types with specific responsible users
- **Streamlined Design**: Removed redundant fields (priority is now in TipoAlerta)
- Duplicate prevention logic

### Key Capabilities
- ✅ **Complete CRUD Operations** for all entities via GraphQL API
- ✅ **Comprehensive Data Validation** with Spring Boot Validation
- ✅ **Business Logic Validation** (duplicate prevention, referential integrity)
- ✅ **Global Exception Handling** with structured error responses
- ✅ **Automatic Mapping** between entities and DTOs using MapStruct
- ✅ **Transaction Management** with proper isolation levels
- ✅ **Comprehensive Logging** for monitoring and debugging
- ✅ **Database Relationships** with proper JPA configurations

## 📊 Data Model

### Entity Relationships
```
TipoAlerta (Alert Type)
├── id: Integer (PK)
├── nombre: String (unique)
├── descripcion: String
├── nivelPrioridad: NivelPrioridad (ManyToOne)
├── tipoEncargado: TipoEncargado (enum)
└── activo: Boolean

NivelPrioridad (Priority Level)
├── id: Integer (PK)
└── nombre: String (unique)

ConfiguracionAlerta (Alert Configuration)
├── id: Integer (PK)
├── tipoAlerta: TipoAlerta (ManyToOne)
└── usuarioResponsableId: Long

TipoEncargado (Enum)
├── CONDUCTOR
├── MECANICO
├── SOPORTE_TECNICO
├── OPERADOR_LOGISTICA
└── SEGURIDAD
```

## 🛠️ Technology Stack

- **Java 21** - Programming language
- **Spring Boot 3.5.5** - Application framework
- **Spring Data JPA** - Data persistence layer
- **GraphQL** - API query language
- **MapStruct** - Bean mapping framework
- **PostgreSQL** - Database (recommended)
- **Lombok** - Boilerplate code reduction
- **SLF4J + Logback** - Logging framework

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- PostgreSQL database
- Maven 3.6+ or use included wrapper

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd alert-management
   ```

2. **Configure database**
   ```properties
   # application.properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/alert_management
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Access GraphQL Playground**
   ```
   http://localhost:8080/graphiql
   ```

## 📝 API Usage

### GraphQL Endpoints

#### Creating an Alert Type
```graphql
mutation {
  createTipoAlerta(input: {
    nombre: "Engine Failure"
    descripcion: "Critical engine system failure alert"
    nivelPrioridadId: 1
    tipoEncargado: MECANICO
  }) {
    id
    nombre
    descripcion
    nivelPrioridad {
      id
      nombre
    }
    tipoEncargado
    activo
  }
}
```

#### Querying Alert Types
```graphql
query {
  tipoAlertas {
    id
    nombre
    descripcion
    nivelPrioridad {
      nombre
    }
    tipoEncargado
    activo
  }
}
```

#### Creating Alert Configuration
```graphql
mutation {
  createConfiguracionAlerta(input: {
    tipoAlertaId: 1
    usuarioResponsableId: 12345
  }) {
    id
    tipoAlerta {
      nombre
      tipoEncargado
    }
    usuarioResponsableId
  }
}
```

## 🏗️ Project Structure

### DTOs Organization
```
DTO/
├── tipoalerta/
│   ├── TipoAlertaCreateRequest.java
│   ├── TipoAlertaUpdateRequest.java
│   └── TipoAlertaResponse.java
├── nivelprioridad/
│   ├── NivelPrioridadCreateRequest.java
│   ├── NivelPrioridadUpdateRequest.java
│   └── NivelPrioridadResponse.java
└── configuracionalerta/
    ├── ConfiguracionAlertaCreateRequest.java
    ├── ConfiguracionAlertaUpdateRequest.java
    └── ConfiguracionAlertaResponse.java
```

### Service Layer
- **Interfaces**: Define service contracts
- **Implementations**: Contain business logic with comprehensive validation
- **Exceptions**: Custom business exceptions for specific error scenarios

### Validation Features
- **Field Validation**: Using Spring Boot Validation annotations
- **Business Logic Validation**: Duplicate prevention, relationship validation
- **Error Handling**: Structured error responses with meaningful messages

## 🧪 Development

### Building the Project
```bash
./gradlew build
```

### Running Tests
```bash
./gradlew test
```

### Generating Documentation
```bash
./gradlew javadoc
```

## 📈 Business Rules

### Alert Type Management
- ✅ Alert type names must be unique (case-insensitive)
- ✅ Each alert type must have an associated priority level
- ✅ Each alert type must specify a responsible type (enum)
- ✅ Alert types can be activated/deactivated
- ✅ Cannot delete alert types referenced by configurations

### Priority Level Management
- ✅ Priority level names must be unique (case-insensitive)
- ✅ Cannot delete priority levels referenced by alert types

### Alert Configuration Management
- ✅ Cannot have duplicate configurations (same alert type + user)
- ✅ Must reference existing alert types
- ✅ User responsibility is handled externally (by ID reference)

## 🔧 Configuration

### Application Properties
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/alert_management
spring.datasource.username=${DB_USERNAME:alert_user}
spring.datasource.password=${DB_PASSWORD:alert_password}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# GraphQL Configuration
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql

# Logging Configuration
logging.level.com.fleetguard360.alert_management=DEBUG
logging.level.org.springframework.web=INFO
```

## 📋 TODO / Roadmap

- [ ] Add integration tests
- [ ] Implement caching layer (Redis)
- [ ] Add audit logging
- [ ] Implement event publishing (Kafka/RabbitMQ)
- [ ] Add API rate limiting
- [ ] Implement soft delete functionality
- [ ] Add API versioning support
- [ ] Create database migration scripts
- [ ] Add monitoring and health checks
- [ ] Implement bulk operations

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions, please contact the FleetGuard360 development team or create an issue in the repository.

---

**FleetGuard360 Alert Management Microservice** - Managing alerts efficiently and reliably.
