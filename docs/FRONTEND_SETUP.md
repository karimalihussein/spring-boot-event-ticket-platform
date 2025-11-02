# Frontend-Backend Integration Setup

## ✅ Configuration Complete

The frontend and backend have been configured to work together.

## 🚀 How to Run Both Projects

### Option 1: Run in Separate Terminals (Recommended)

#### Terminal 1 - Backend (Spring Boot):
```bash
cd /Users/karim/Downloads/tickets
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
mvn spring-boot:run
```
Backend will run on: **http://localhost:8084**

#### Terminal 2 - Frontend (Vite):
```bash
cd /Users/karim/Downloads/tickets/ui
npm install  # Only needed first time
npm run dev
```
Frontend will run on: **http://localhost:5173**

### Option 2: Using a Script

You can create a script to run both at once (see `run-both.sh` below)

## 🔧 Configuration Details

### Backend Configuration:
- **Port**: 8084
- **API Base URL**: `http://localhost:8084/api/v1`
- **CORS**: Enabled for `http://localhost:5173` (Vite dev server)

### Frontend Configuration:
- **Port**: 5173 (Vite default)
- **API Proxy**: Configured in `vite.config.ts` to proxy `/api` requests to `http://localhost:8084`
- **OIDC**: Keycloak at `http://localhost:8081/realms/event-tickets-platform`

## 📡 How API Calls Work

The frontend makes API calls like:
```typescript
fetch("/api/v1/events", { ... })
```

Vite's proxy automatically forwards these to:
```
http://localhost:8084/api/v1/events
```

So the frontend doesn't need to know about the backend port!

## 🔐 Authentication Flow

1. User visits frontend at `http://localhost:5173`
2. Frontend redirects to Keycloak for login
3. Keycloak redirects back to `http://localhost:5173/callback`
4. Frontend receives JWT token
5. Frontend includes token in API requests: `Authorization: Bearer <token>`
6. Backend validates token with Keycloak

## ✅ Testing the Connection

1. **Start Backend** (port 8084):
   ```bash
   cd /Users/karim/Downloads/tickets
   export JAVA_HOME=$(/usr/libexec/java_home -v 21)
   mvn spring-boot:run
   ```

2. **Start Frontend** (port 5173):
   ```bash
   cd /Users/karim/Downloads/tickets/ui
   npm run dev
   ```

3. **Open Browser**:
   - Frontend: http://localhost:5173
   - Backend API: http://localhost:8084/actuator/health

## 🛠️ Troubleshooting

### CORS Errors
If you see CORS errors, make sure:
- Backend is running on port 8084
- Frontend is running on port 5173
- SecurityConfig.java includes the frontend origin

### API Connection Issues
- Check backend logs for incoming requests
- Verify Vite proxy configuration in `vite.config.ts`
- Check browser Network tab for failed requests

### Port Already in Use
If port 8084 is in use:
```bash
# Change backend port in application.properties
server.port=9090
# Update vite.config.ts proxy target accordingly
```

If port 5173 is in use, Vite will automatically use the next available port.

## 📝 Notes

- The frontend proxy only works in development mode (`npm run dev`)
- For production builds, you'll need to configure the API URL differently
- Make sure Keycloak is running on port 8081 for authentication to work

