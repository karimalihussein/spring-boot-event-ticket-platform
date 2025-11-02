#!/bin/bash

# Script to run both frontend and backend together

echo "🚀 Starting Tickets Platform..."
echo ""

# Function to cleanup on exit
cleanup() {
    echo ""
    echo "🛑 Stopping all processes..."
    kill $BACKEND_PID $FRONTEND_PID 2>/dev/null
    exit
}

trap cleanup SIGINT SIGTERM

# Start Backend
echo "📦 Starting Backend (Spring Boot) on port 8084..."
cd "$(dirname "$0")"
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
mvn spring-boot:run > /tmp/backend.log 2>&1 &
BACKEND_PID=$!
echo "✅ Backend started (PID: $BACKEND_PID)"
echo "   Logs: tail -f /tmp/backend.log"
echo ""

# Wait a bit for backend to start
sleep 3

# Start Frontend
echo "⚛️  Starting Frontend (Vite) on port 5173..."
cd ui
npm run dev > /tmp/frontend.log 2>&1 &
FRONTEND_PID=$!
echo "✅ Frontend started (PID: $FRONTEND_PID)"
echo "   Logs: tail -f /tmp/frontend.log"
echo ""

echo "🎉 Both services are running!"
echo ""
echo "📍 Frontend: http://localhost:5173"
echo "📍 Backend:  http://localhost:8084"
echo ""
echo "Press Ctrl+C to stop both services"

# Wait for user interrupt
wait

