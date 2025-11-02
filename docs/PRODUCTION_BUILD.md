# Production Build Guide

## Quick Answer: Do I Need to Move Build to Static Folder?

**For Development**: ❌ NO - Keep them separate (frontend on 5173, backend on 8084)

**For Production**: ✅ YES - Build frontend and copy to `src/main/resources/static/`

## 🚀 Quick Production Build

Run this script to build and copy frontend:

```bash
./build-frontend.sh
```

Then start Spring Boot:
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
mvn spring-boot:run
```

The frontend will be served at: **http://localhost:8084**

## 📝 Manual Steps

If you prefer to do it manually:

1. **Build the frontend:**
   ```bash
   cd ui
   npm install
   npm run build
   ```

2. **Copy to static folder:**
   ```bash
   cd ..
   rm -rf src/main/resources/static/*
   cp -r ui/dist/* src/main/resources/static/
   ```

3. **Run Spring Boot:**
   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home -v 21)
   mvn spring-boot:run
   ```

## 🔧 How It Works

### Development Mode (Separate)
- Frontend: `http://localhost:5173` (Vite dev server)
- Backend: `http://localhost:8084` (Spring Boot)
- Vite proxy forwards `/api/*` to backend

### Production Mode (Integrated)
- Everything: `http://localhost:8084`
- Spring Boot serves static files from `/static/`
- API calls go to `/api/v1/*`
- React Router handles client-side routing

## 📁 Directory Structure After Build

```
src/main/resources/static/
├── index.html          # React app entry point
├── assets/             # JS, CSS, images
│   ├── index-*.js
│   ├── index-*.css
│   └── ...
├── vite.svg
└── ... (other assets)
```

## ⚙️ Configuration Details

### WebConfig.java
- Handles SPA routing (serves `index.html` for all non-API routes)
- Allows React Router to handle client-side navigation

### SecurityConfig.java
- Allows public access to static assets (`/assets/**`, `index.html`, etc.)
- Requires authentication for API endpoints (`/api/**`)

### Vite Config
- Base path set to `/` (served from Spring Boot root)
- Build output goes to `dist/` folder

## 🎯 When to Use Each Approach

### Use Separate (Development):
- ✅ Faster hot-reload
- ✅ Easier debugging
- ✅ Frontend and backend can be worked on independently

### Use Integrated (Production):
- ✅ Single deployment
- ✅ No CORS issues
- ✅ Single port
- ✅ Simpler deployment

## 📌 Important Notes

1. **Always run `./build-frontend.sh` before deploying**
2. **Don't commit `src/main/resources/static/` if it contains build files** (add to `.gitignore`)
3. **For production, update Keycloak redirect URI** from `http://localhost:5173/callback` to `http://localhost:8084/callback`

