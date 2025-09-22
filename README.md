# Fleet Guard 360

## API Gateway
This project is the API Gateway for the microservices architecture. 
It serves as the main entry point for frontend clients, handling request routing,
JWT authentication, CORS, and communication with backend services.

The gateway centralizes security, simplifies the frontend’s access to multiple microservices,
and ensures a clean, scalable, and maintainable architecture.

The application runs on port **8085** by default.

## 🚀 🛠️ Stack

![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![GraphQL](https://img.shields.io/badge/GraphQL-E10098?logo=graphql&logoColor=white)
![OpenFeign](https://img.shields.io/badge/OpenFeign-007ACC)
![JUnit](https://img.shields.io/badge/JUnit-25A162?logo=junit5&logoColor=white)

## 🔑 Login Mutation

Use the following GraphQL mutation to login:

```graphql
mutation {
  login(email: "user@example.com", password: "yourPassword") {
    email
    jwt
    message
    status
  }
}
```
### Arguments:

- `email`: User's email address.
- `password`: User's password.
- `jwt`: JWT token returned if authentication is successful.
- `message`: Descriptive message of the successful login.
- `status`: indicates that the operation was successful (true).

### 💡 Example Responses

Success:
```json
{
  "data": {
    "login": {
      "email": "juan.perez@example.co",
      "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "message": "Login successful",
      "status": true
    }
  }
}
```
Error:
```json
{
  "errors": [
    {
      "message": "Login failed: Bad credentials",
      "locations": [],
      "extensions": {
        "classification": "UNAUTHORIZED"
      }
    }
  ],
  "data": {
    "login": null
  }
}
```