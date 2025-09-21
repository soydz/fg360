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
│   │   ├── configuracionalerta/ # Alert Configuration DTOs
│   │   └── common/       # Shared DTOs
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
- **TipoAlerta (Alert Type)**: Catalog of available alert types with activation/deactivation capability
- **NivelPrioridad (Priority Level)**: Priority levels with color coding for visual representation
- **ConfiguracionAlerta (Alert Configuration)**: Links alert types with priority levels and responsible users

### Key Capabilities
- ✅ **CRUD Operations** for all entities via GraphQL API
- ✅ **Data Validation** with comprehensive validation rules
- ✅ **Business Logic Validation** (duplicate prevention, referential integrity)
- ✅ **Global Exception Handling** with structured error responses
- ✅ **Automatic Mapping** between entities and DTOs using MapStruct
- ✅ **Transaction Management** with proper isolation levels
- ✅ **Comprehensive Logging** for monitoring and debugging
- ✅ **Database Relationships** with proper JPA configurations

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming Language |
| **Spring Boot** | 3.5.5 | Application Framework |
| **Spring Data JPA** | 3.5.5 | Data Access |
| **Spring GraphQL** | Latest | GraphQL API |
| **PostgreSQL** | Latest | Database |
| **MapStruct** | 1.5.5 | Object Mapping |
| **Lombok** | Latest | Code Generation |
| **Jakarta Validation** | Latest | Data Validation |

## 📋 Prerequisites

- **Java 21** or higher
- **PostgreSQL** 12+ running on localhost:5432
- **Gradle** 8+ (or use included wrapper)
- **Git** for version control

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd alert-management
```



## 📡 API Documentation

### GraphQL Endpoint
- **URL**: `http://localhost:8084/graphql`
- **GraphiQL IDE**: `http://localhost:8084/graphiql` (for development)

### Core Operations

#### Alert Types (TipoAlerta)
```graphql
# Query all alert types
query {
  tipoAlertas {
    id
    nombre
    descripcion
    activo
  }
}

# Create new alert type
mutation {
  createTipoAlerta(input: {
    nombre: "Engine Failure"
    descripcion: "Critical engine malfunction alert"
  }) {
    id
    nombre
    activo
  }
}

# Update alert type
mutation {
  updateTipoAlerta(id: 1, input: {
    nombre: "Engine Critical Failure"
    activo: true
  }) {
    id
    nombre
    activo
  }
}
```

#### Priority Levels (NivelPrioridad)
```graphql
# Query all priority levels
query {
  nivelesPrioridad {
    id
    nombre
    colorHex
  }
}

# Create priority level
mutation {
  createNivelPrioridad(input: {
    nombre: "Critical"
    colorHex: "#FF0000"
  }) {
    id
    nombre
    colorHex
  }
}
```

#### Alert Configurations (ConfiguracionAlerta)
```graphql
# Query alert configurations
query {
  configuracionesAlerta {
    id
    tipoAlerta {
      nombre
    }
    nivelPrioridad {
      nombre
      colorHex
    }
    usuarioResponsableId
    parametroDisparador
  }
}

# Create alert configuration
mutation {
  createConfiguracionAlerta(input: {
    tipoAlertaId: 1
    nivelPrioridadId: 1
    usuarioResponsableId: 12345
    parametroDisparador: "temperature > 80"
  }) {
    id
    usuarioResponsableId
  }
}
```

## 🗄️ Database Schema

### Tables Structure
```sql
-- Alert Types
CREATE TABLE tipo_alerta (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    activo BOOLEAN NOT NULL DEFAULT true
);

-- Priority Levels
CREATE TABLE nivel_prioridad (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    color_hex VARCHAR(7)
);

-- Alert Configurations
CREATE TABLE configuracion_alerta (
    id SERIAL PRIMARY KEY,
    tipo_alerta_id INTEGER NOT NULL REFERENCES tipo_alerta(id),
    nivel_prioridad_id INTEGER NOT NULL REFERENCES nivel_prioridad(id),
    usuario_responsable_id BIGINT NOT NULL,
    parametro_disparador VARCHAR(255),
    UNIQUE(tipo_alerta_id, nivel_prioridad_id, usuario_responsable_id)
);
```

## 🔧 Configuration

### Application Properties
```properties
# Application Configuration
spring.application.name=alert-management
server.port=8084
spring.profiles.active=dev

# Database Configuration (dev profile)
spring.datasource.url=jdbc:postgresql://localhost:5432/fleetguard_db
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:your_password}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

## 🧪 Testing

### Run Tests
```bash
./gradlew test
```

### Test Coverage
```bash
./gradlew jacocoTestReport
```

## 📝 API Validation Rules

### TipoAlerta (Alert Type)
- `nombre`: Required, max 100 characters, unique
- `descripcion`: Optional, max 1000 characters

### NivelPrioridad (Priority Level)
- `nombre`: Required, max 50 characters, unique
- `colorHex`: Optional, must match pattern `#[A-Fa-f0-9]{6}`

### ConfiguracionAlerta (Alert Configuration)
- `tipoAlertaId`: Required, must be positive, must exist
- `nivelPrioridadId`: Required, must be positive, must exist
- `usuarioResponsableId`: Required, must be positive
- `parametroDisparador`: Optional, max 255 characters
- Unique constraint on (tipoAlertaId, nivelPrioridadId, usuarioResponsableId)

## 🚨 Error Handling

The API provides structured error responses:

```json
{
  "timestamp": "2025-09-21T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "nombre: nombre es obligatorio",
  "path": "/graphql",
  "traceId": "abc123"
}
```

### Common Error Codes
- **400 Bad Request**: Validation errors
- **404 Not Found**: Entity not found
- **409 Conflict**: Duplicate data or constraint violations
- **500 Internal Server Error**: Unexpected errors

## 🔍 Monitoring and Logging

### Logging Levels
- **DEBUG**: Detailed operation logs
- **INFO**: Important business events
- **WARN**: Business rule violations
- **ERROR**: System errors

### Log Examples
```
INFO  - TipoAlerta creado id=1 nombre='Engine Failure'
WARN  - Intento de crear TipoAlerta duplicado nombre='Engine Failure'
DEBUG - Actualizando ConfiguracionAlerta id=5
```

## 🚀 Deployment

### Docker Support (Future Enhancement)
```dockerfile
FROM openjdk:21-jre-slim
COPY build/libs/alert-management-*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Environment Variables
```bash
export DB_USERNAME=postgres
export DB_PASSWORD=secure_password
export SPRING_PROFILES_ACTIVE=prod
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Use Lombok annotations for boilerplate code
- Write comprehensive JavaDoc for public APIs
- Maintain clean architecture separation

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


---

**FleetGuard360 Team** - 
