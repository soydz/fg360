# Fleet Guard 360

## 🚀 🛠️ Stack

![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud Gateway](https://img.shields.io/badge/Spring%20Cloud%20Gateway-6DB33F?logo=spring&logoColor=white)
![GraphQL](https://img.shields.io/badge/GraphQL-E10098?logo=graphql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?logo=JSON%20web%20tokens&logoColor=white)

## API Gateway
This project is the API Gateway for the microservices architecture. 
It serves as the main entry point for frontend clients, handling request routing,
JWT authentication, CORS, and communication with backend services.

The gateway centralizes security, simplifies the frontend's access to multiple microservices,
and ensures a clean, scalable, and maintainable architecture.

The application runs on port **8085** by default.

## 🛣️ Available Endpoints

The API Gateway routes requests to two main microservices:

### 1. Authentication Service - `/auth/login`
- **Purpose**: Handles user authentication
- **Route**: `POST /auth/login` 
- **Security**: Open endpoint (no JWT token required)
- **Content-Type**: `application/json`

📚 **For detailed API documentation, visit the [Authentication Service Repository](https://github.com/your-org/auth-service)**

### 2. Alerts Service (GraphQL) - `/alerts/graphql/**`
- **Purpose**: Handles alert management through GraphQL
- **Route**: All requests to `/alerts/graphql/`
- **Security**: Protected endpoint (requires JWT token)
- **Content-Type**: `application/json`
- **Examples**:
  - `POST /alerts/graphql` - GraphQL queries and mutations
  - Access GraphQL Playground at `/alerts/graphql` (if enabled)

📚 **For detailed GraphQL schema and queries, visit the [Alerts Service Repository](https://github.com/your-org/alerts-service)**

## 🔐 Authentication Flow

1. **Login**: Send credentials to `/auth/login` to get JWT token
2. **Protected Requests**: Include JWT token in `Authorization: Bearer <token>` header
3. **GraphQL Operations**: Use the token to access `/alerts/graphql` endpoint
