# RESTful Calculator

A RESTful calculator API built with Spring Boot, using Apache Kafka for inter-module communication.

## Architecture

The project consists of two modules:
- **calculator**: Business logic module that performs calculations
- **rest**: REST API module that handles HTTP requests

Communication between modules is handled via Apache Kafka.


## Features

-  RESTful API with basic calculator operations (sum, subtract, multiply, divide)
- Support for arbitrary precision signed decimal numbers
- Multi-module Spring Boot project
- Apache Kafka for inter-module communication
-  SLF4J logging with request ID propagation via MDC
-  Unique request identifiers
-  Docker support
- Unit tests
-  Comprehensive logging


## Supported Operations

| Operation | Endpoint | Example |
|-----------|----------|---------|
| Addition | `GET /sum?a=1&b=2` | 1 + 2 = 3 |
| Subtraction | `GET /subtract?a=5&b=2` | 5 - 2 = 3 |
| Multiplication | `GET /multiply?a=3&b=4` | 3 × 4 = 12 |
| Division | `GET /divide?a=10&b=2` | 10 ÷ 2 = 5 |

### API Response Format

```json
{
  "result": "3",
  "requestId": "uuid-string",
  "success": true,
  "error": null
}
```

## Building the Project

### Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Gradle 7.0 or higher

### Build Commands

```bash
# Build all modules
./gradlew build

# Run tests
./gradlew test


```

## Running the Project


```bash
# Start all services
docker-compose up --build


# Stop all services
docker-compose down
```

## Testing the API

You can test the API through:
- **Direct browser access**: Copy the URLs provided for each operation
- **Command line**: Use the curl commands provided below

Make sure all services are running via Docker Compose before testing.

### Addition
```bash
curl "http://localhost:8082/api/calculator/sum?a=1.5&b=2.3"
```
Expected output:
```json
{"result":"3.8","requestId":"a6f6238f-967b-4bc9-b3a3-8d05f894b4a1","success":true,"error":null}
```

### Subtraction
```bash
curl "http://localhost:8082/api/calculator/subtract?a=10.5&b=3.2"
```
Expected output:
```json
{"result":"7.3","requestId":"a6f6238f-967b-4bc9-b3a3-8d05f894b4a2","success":true,"error":null}
```

### Multiplication
```bash
curl "http://localhost:8082/api/calculator/multiply?a=4.5&b=2.0"
```
Expected output:
```json
{"result":"9.0","requestId":"b1d7426e-82ac-41bb-bfe8-9c1a2346a91b","success":true,"error":null}
```

### Division
```bash
curl "http://localhost:8082/api/calculator/divide?a=10&b=5"
```
Expected output:
```json
{"result":"2.0","requestId":"3e72fbb9-d4b1-4423-a94f-6b15c432a6f0","success":true,"error":null}
```

### Division by Zero (Error Handling)
```bash
curl "http://localhost:8082/api/calculator/divide?a=10&b=0"
```
Expected output:
```json
{"result":null,"requestId":"7f3d8cb7-5c1b-4596-9d5d-b4a2d6f08b23","success":false,"error":"Division by zero is not allowed"}
```

## Conclusion

This project successfully implements a complete microservice-based basic calculator API using industry standards:

- **Modular Design**: Two dedicated modules with clear separation of concerns
- **Reliable Communication**: Apache Kafka enables asynchronous inter-module communication
- **Enterprise Features**: Full logging with request traceability and comprehensive error handling
- **Production-Ready**: Docker containerization with build automation and scalable deployment
- **Quality Assurance**: Comprehensive unit tests ensuring reliable behavior

This implementation showcases modern Java microservice design patterns that deliver production-quality software with maintainable, testable, and deployable components.
