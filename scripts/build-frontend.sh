#!/bin/bash

# Script to build frontend and copy it to Spring Boot static folder

set -e

echo "🏗️  Building Frontend..."

# Navigate to frontend directory
cd "$(dirname "$0")/ui"

# Build the frontend
echo "📦 Running npm install..."
npm install --legacy-peer-deps

echo "🔨 Building frontend for production..."
npm run build 

# Check if build was successful
if [ ! -d "dist" ]; then
    echo "❌ Build failed: dist folder not found"
    exit 1
fi

# Navigate back to project root
cd ..

# Remove old static files (except META-INF if it exists)
echo "🧹 Cleaning old static files..."
rm -rf src/main/resources/static/*
mkdir -p src/main/resources/static

# Copy built files to static folder
echo "📋 Copying built files to src/main/resources/static/..."
cp -r ui/dist/* src/main/resources/static/

echo "✅ Frontend built and copied successfully!"
echo ""
echo "📍 Static files are now in: src/main/resources/static/"
echo "📍 You can now run Spring Boot and the frontend will be served at: http://localhost:8084"
echo ""
echo "Next steps:"
echo "  1. Run: export JAVA_HOME=\$(/usr/libexec/java_home -v 21) && mvn spring-boot:run"
echo "  2. Open: http://localhost:8084"
echo ""

