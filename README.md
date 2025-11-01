# Event Tickets Platform

An event ticket management platform built with Spring Boot, providing features for event creation, ticket management, and QR code validation.

## Features

- **Event Management**: Create and manage events with details like name, venue, dates, and sales windows
- **Ticket Types**: Define multiple ticket types for each event with different pricing and capacity
- **Ticket System**: Issue tickets to purchasers with QR codes for validation
- **QR Code Validation**: Validate tickets using QR codes at event entry
- **Role Management**: Support for event organizers, staff, and attendees
- **OAuth2 Security**: Integration with Keycloak for authentication and authorization
- **Health Monitoring**: Spring Boot Actuator endpoints for health checks

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.4.10
- **Spring Security**: OAuth2 Resource Server
- **Spring Data JPA**: For database operations
- **PostgreSQL**: Primary database
- **Keycloak**: Authentication and authorization server
- **Lombok**: For reducing boilerplate code
- **MapStruct**: For object mapping (configured)
- **Docker**: Container orchestration for local development

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose
- PostgreSQL (optional if using Docker)

## Quick Start

### 1. Start Infrastructure Services

Start PostgreSQL, Keycloak, and Adminer using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- **PostgreSQL** on port `5433`
- **Keycloak** on port `8081` (admin: admin/admin)
- **Adminer** on port `8888` (database management UI)

### 2. Configure Keycloak

1. Access Keycloak admin console at http://localhost:8081
2. Create a realm named `event-tickets-platform`
3. Configure clients and roles as needed

### 3. Run the Application

Using Maven:

```bash
./mvnw spring-boot:run
```

Or using the Maven wrapper on Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8080` (default Spring Boot port).

## Configuration

### Application Properties

The main configuration is in `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/tickets
spring.datasource.username=postgres
spring.datasource.password=postgres

# Keycloak Configuration
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8081/realms/event-tickets-platform

# Actuator Configuration
management.endpoints.web.exposure.include=health,info
```

### Environment-Specific Configuration

- `application.properties`: Main configuration
- `application-dev.properties`: Development-specific overrides

## Database Access

### Using Adminer

Access the database management UI at http://localhost:8888 and log in with:
- Server: `postgres`
- Username: `postgres`
- Password: `postgres`
- Database: `tickets`

## API Endpoints

### Public Endpoints

- `GET /actuator/health` - Health check endpoint

### Protected Endpoints

All other endpoints require OAuth2 authentication via Keycloak.

## Project Structure

```
src/
├── main/
│   ├── java/com/personal/tickets/
│   │   ├── config/           # Configuration classes
│   │   ├── domain/
│   │   │   ├── Entities/     # JPA entities
│   │   │   ├── Enums/        # Enumeration types
│   │   │   └── Requests/     # Request DTOs
│   │   ├── exceptions/       # Custom exceptions
│   │   ├── filters/          # Security filters
│   │   ├── repositories/     # Data access layer
│   │   └── services/         # Business logic
│   └── resources/
│       ├── application.properties
│       └── application-dev.properties
└── test/
    └── java/                 # Test classes
```

## Core Entities

- **User**: Represents users (organizers, staff, attendees)
- **Event**: Event information with dates, venue, and status
- **TicketType**: Types of tickets available for an event
- **Ticket**: Individual tickets issued to purchasers
- **QrCode**: QR codes associated with tickets
- **TicketValidation**: Validation records for ticket scans

## Development

### Building the Project

```bash
./mvnw clean package
```

### Running Tests

```bash
./mvnw test
```

### Development Tools

The project includes Spring Boot DevTools for hot reloading:
- Automatic restart on code changes
- LiveReload support

## Health Checks

The application exposes health check endpoints:

- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For questions or issues, please open an issue in the repository.

