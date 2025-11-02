# Event Tickets Platform

A full-stack event ticket management platform built with Spring Boot (backend) and React + Vite (frontend), providing features for event creation, ticket management, QR code validation, and OAuth2 authentication.

## 🎯 Features

### Backend Features

- **Event Management**: Create and manage events with details like name, venue, dates, and sales windows
- **Ticket Types**: Define multiple ticket types for each event with different pricing and capacity
- **Ticket System**: Issue tickets to purchasers with QR codes for validation
- **QR Code Validation**: Validate tickets using QR codes at event entry
- **Role Management**: Support for event organizers, staff, and attendees
- **OAuth2 Security**: Integration with Keycloak for authentication and authorization
- **Health Monitoring**: Spring Boot Actuator endpoints for health checks
- **RESTful API**: RESTful endpoints for all operations
- **Error Handling**: Comprehensive global exception handling with JSON error responses

### Frontend Features

- **React SPA**: Single-page application built with React and TypeScript
- **Event Browsing**: Browse published events with search and pagination
- **Ticket Purchase**: Purchase tickets for published events
- **Dashboard**: Organizer dashboard for managing events and tickets
- **QR Code Validation**: Validate tickets using QR code scanning
- **OAuth2 Authentication**: Secure login using Keycloak
- **Responsive Design**: Modern UI built with Tailwind CSS and shadcn/ui components

## 🛠 Technology Stack

### Backend

- **Java**: 21
- **Spring Boot**: 3.4.10
- **Spring Security**: OAuth2 Resource Server
- **Spring Data JPA**: For database operations
- **PostgreSQL**: Primary database
- **Keycloak**: Authentication and authorization server
- **Lombok**: For reducing boilerplate code
- **MapStruct**: For object mapping between DTOs and entities
- **Maven**: Build tool and dependency management

### Frontend

- **React**: 18+ with TypeScript
- **Vite**: Build tool and dev server
- **Tailwind CSS**: Utility-first CSS framework
- **shadcn/ui**: UI component library
- **React Router**: Client-side routing
- **react-oidc-context**: OAuth2/OpenID Connect client
- **Node.js**: Runtime environment
- **npm**: Package manager

### Infrastructure

- **Docker**: Container orchestration for local development
- **Docker Compose**: Multi-container Docker application management

## 📋 Prerequisites

### Required

- **Java 21** or higher
- **Maven 3.6+**
- **Node.js 18+** and npm
- **Docker** and Docker Compose

### Optional

- **PostgreSQL** (if not using Docker)
- **Keycloak** (if not using Docker)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd tickets
```

### 2. Start Infrastructure Services

Start PostgreSQL, Keycloak, and Adminer using Docker Compose:

```bash
docker-compose up -d
```

This will start:

- **PostgreSQL** on port `5433`
- **Keycloak** on port `8081` (admin: admin/admin)
- **Adminer** on port `8888` (database management UI)

### 3. Configure Keycloak

1. Access Keycloak admin console at http://localhost:8081
2. Log in with username: `admin` and password: `admin`
3. Create a realm named `event-tickets-platform`
4. Create a client:
   - Client ID: `event-tickets-platform-app`
   - Client authentication: OFF (public client)
   - Valid redirect URIs: `http://localhost:5173/callback`
   - Web origins: `http://localhost:5173`

### 4. Configure Environment Variables

#### Backend

Copy the example environment file and configure it:

```bash
cp .env.example .env
```

Edit `.env` with your actual values, or set environment variables with `SPRING_` prefix.

#### Frontend

Copy the example environment file and configure it:

```bash
cd ui
cp .env.example .env
```

Edit `ui/.env` with your actual values. The defaults should work for local development.

### 5. Run the Application

#### Option A: Run Backend and Frontend Separately (Recommended for Development)

**Terminal 1 - Backend:**

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)  # macOS/Linux
mvn spring-boot:run
```

**Terminal 2 - Frontend:**

```bash
cd ui
npm install  # Only needed first time
npm run dev
```

#### Option B: Use Helper Scripts

**Run both projects:**

```bash
./scripts/run-both.sh
```

**Build frontend for production:**

```bash
./scripts/build-frontend.sh
```

### 6. Access the Application

- **Frontend (Development)**: http://localhost:5173
- **Backend API**: http://localhost:8084
- **Backend (Production Build)**: http://localhost:8084 (serves frontend from `/`)
- **Keycloak Admin**: http://localhost:8081
- **Adminer (Database)**: http://localhost:8888
  - Server: `postgres`
  - Username: `postgres`
  - Password: `postgres`
  - Database: `tickets`

## ⚙️ Configuration

### Backend Configuration

The main configuration is in `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8084

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/tickets
spring.datasource.username=postgres
spring.datasource.password=postgres

# Keycloak Configuration
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8081/realms/event-tickets-platform

# Actuator Configuration
management.endpoints.web.exposure.include=health,info
```

### Environment Variables

#### Backend Environment Variables

You can override any Spring Boot property using environment variables with the `SPRING_` prefix. See `.env.example` for available variables:

- `SERVER_PORT` - Backend server port
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` - Keycloak issuer URI

#### Frontend Environment Variables

All frontend environment variables must be prefixed with `VITE_` to be exposed to the client. See `ui/.env.example` for available variables:

- `VITE_API_BASE_URL` - Backend API base URL (default: http://localhost:8084)
- `VITE_KEYCLOAK_AUTHORITY` - Keycloak authority URL
- `VITE_KEYCLOAK_CLIENT_ID` - Keycloak client ID
- `VITE_REDIRECT_URI` - OAuth redirect URI
- `VITE_PORT` - Vite dev server port (default: 5173)
- `VITE_BASE_PATH` - Base path for production builds (default: /)

### Environment-Specific Configuration

- `application.properties`: Main configuration
- `application-dev.properties`: Development-specific overrides

## 🏗️ Project Structure

```
tickets/
├── src/                          # Backend source code
│   ├── main/
│   │   ├── java/com/personal/tickets/
│   │   │   ├── config/          # Configuration classes (Security, Web, JPA)
│   │   │   ├── controllers/     # REST controllers
│   │   │   ├── domain/
│   │   │   │   ├── Entities/    # JPA entities
│   │   │   │   ├── Enums/       # Enumeration types
│   │   │   │   └── Requests/   # Request DTOs
│   │   │   ├── dtos/            # Data Transfer Objects
│   │   │   ├── exceptions/     # Custom exceptions
│   │   │   ├── filters/         # Security filters
│   │   │   ├── handlers/       # Exception handlers
│   │   │   ├── mappers/         # MapStruct mappers
│   │   │   ├── repositories/    # Data access layer
│   │   │   └── services/        # Business logic
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── banner.txt       # Spring Boot banner
│   │       └── static/          # Frontend production build
│   └── test/                    # Test classes
├── ui/                           # Frontend source code
│   ├── src/
│   │   ├── components/          # React components
│   │   ├── pages/               # Page components
│   │   ├── lib/                 # Utilities and API client
│   │   ├── domain/              # TypeScript domain models
│   │   └── hooks/               # Custom React hooks
│   ├── public/                  # Static assets
│   ├── dist/                    # Production build output
│   └── vite.config.ts           # Vite configuration
├── scripts/                     # Helper scripts
│   ├── build-frontend.sh        # Build frontend for production
│   └── run-both.sh              # Run backend and frontend together
├── docs/                        # Documentation
├── .env.example                 # Backend environment variables template
├── ui/.env.example              # Frontend environment variables template
├── docker-compose.yml           # Docker services configuration
└── pom.xml                      # Maven configuration
```

## 📊 Core Entities

- **User**: Represents users (organizers, staff, attendees)
- **Event**: Event information with dates, venue, status, and sales windows
- **TicketType**: Types of tickets available for an event with pricing and capacity
- **Ticket**: Individual tickets issued to purchasers
- **QrCode**: QR codes associated with tickets
- **TicketValidation**: Validation records for ticket scans

## 🔌 API Endpoints

### Base URL

- Development: `http://localhost:8084/api/v1`
- Production: Configured via environment variables

### Public Endpoints

- `GET /actuator/health` - Health check endpoint
- `GET /actuator/info` - Application information
- `GET /api/v1/published-events` - List published events (public browsing)
- `GET /api/v1/published-events/{id}` - Get published event details
- `GET /api/v1/published-events?q={query}&page={page}&size={size}` - Search published events

### Protected Endpoints (Require OAuth2 Authentication)

#### Events

- `POST /api/v1/events` - Create a new event
- `GET /api/v1/events` - List user's events
- `GET /api/v1/events/{id}` - Get event details
- `PUT /api/v1/events/{id}` - Update an event
- `DELETE /api/v1/events/{id}` - Delete an event

#### Tickets

- `POST /api/v1/events/{eventId}/ticket-types/{ticketTypeId}/tickets` - Purchase a ticket
- `GET /api/v1/tickets` - List user's tickets
- `GET /api/v1/tickets/{id}` - Get ticket details
- `GET /api/v1/tickets/{id}/qr-codes` - Get ticket QR code

#### Ticket Validation

- `POST /api/v1/ticket-validations` - Validate a ticket

## 🧪 Development

### Building the Project

**Backend:**

```bash
./mvnw clean package
```

**Frontend:**

```bash
cd ui
npm run build
```

**Build Frontend for Production (copies to backend static folder):**

```bash
./scripts/build-frontend.sh
```

### Running Tests

**Backend:**

```bash
./mvnw test
```

**Frontend:**

```bash
cd ui
npm test
```

### Development Tools

#### Backend

- **Spring Boot DevTools**: Automatic restart on code changes
- **LiveReload**: Support for live reloading
- **Hibernate**: SQL logging enabled in development

#### Frontend

- **Vite HMR**: Hot module replacement for instant updates
- **TypeScript**: Type checking and IntelliSense
- **ESLint**: Code linting

## 📦 Production Build

### Build Frontend for Production

The frontend can be built and served from the Spring Boot backend:

```bash
./scripts/build-frontend.sh
```

This will:

1. Build the React application
2. Copy the built files to `src/main/resources/static/`
3. Serve the frontend from the backend at `http://localhost:8084`

### Running Production Build

After building, run the Spring Boot application:

```bash
mvn spring-boot:run
```

The frontend will be accessible at `http://localhost:8084`, and API endpoints at `http://localhost:8084/api/v1`.

## 🔐 Security

### Authentication Flow

1. User visits frontend at `http://localhost:5173`
2. Frontend redirects to Keycloak for login
3. Keycloak authenticates the user
4. Keycloak redirects back to `http://localhost:5173/callback` with authorization code
5. Frontend exchanges code for JWT token
6. Frontend includes token in API requests: `Authorization: Bearer <token>`
7. Backend validates token with Keycloak

### CORS Configuration

CORS is configured to allow requests from:

- `http://localhost:5173` (Vite dev server)
- `http://localhost:3000` (Alternative frontend port)
- Production frontend URL (configure via environment variables)

## 🏥 Health Checks

The application exposes health check endpoints:

- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information

## 🐛 Troubleshooting

### Common Issues

1. **Port Already in Use**

   - Backend default port: 8084
   - Frontend default port: 5173
   - Change ports in `application.properties` or `.env` files

2. **Database Connection Failed**

   - Ensure PostgreSQL is running: `docker-compose ps`
   - Check database credentials in `application.properties`

3. **Keycloak Connection Failed**

   - Ensure Keycloak is running: `docker-compose ps`
   - Verify realm and client configuration

4. **CORS Errors**

   - Check `SecurityConfig.java` for allowed origins
   - Verify frontend URL matches CORS configuration

5. **Environment Variables Not Loading**
   - Ensure `.env` files exist (copy from `.env.example`)
   - For frontend, variables must start with `VITE_`
   - Restart the application after changing environment variables

## 📚 Documentation

Additional documentation can be found in the `docs/` directory:

- `FRONTEND_SETUP.md` - Frontend-backend integration guide
- `PRODUCTION_BUILD.md` - Production build instructions
- `HELP.md` - Additional help and tips

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Run tests (`mvn test` and `npm test`)
5. Commit your changes (`git commit -m 'Add some amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 💬 Support

For questions or issues, please open an issue in the repository.

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- React team for the frontend library
- Vite team for the fast build tool
- Keycloak team for the authentication server
- All open-source contributors
