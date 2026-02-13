#!/bin/bash

APP_HOME=$(dirname "$0")

# Set Java options for container environment
JAVA_OPTS="-Xmx512m -Xms256m"
JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=prod"

# External configuration paths
CONFIG_PATH="$APP_HOME/config"
LOGBACK_CONFIG="$CONFIG_PATH/logback.xml"
APP_CONFIG="$CONFIG_PATH/application.yaml"

# Classpath setup
CLASSPATH="$APP_HOME/url-shortener-1.0.0.jar:$APP_HOME/lib/*"

# Spring configuration
SPRING_CONFIG="-Dspring.config.location=file:$APP_CONFIG"
LOGBACK_CONFIG="-Dlogback.configurationFile=$LOGBACK_CONFIG"

# Function to handle graceful shutdown
shutdown_handler() {
    echo "Received shutdown signal, gracefully stopping application..."
    if [ ! -z "$JAVA_PID" ]; then
        kill -TERM $JAVA_PID
        wait $JAVA_PID
    fi
    echo "Application stopped gracefully"
    exit 0
}

# Register signal handlers for graceful shutdown
trap shutdown_handler SIGTERM SIGINT

echo "Starting URL Shortener Application..."
echo "App Home: $APP_HOME"
echo "Config Path: $CONFIG_PATH"
echo "Logback Config: $LOGBACK_CONFIG"
echo "App Config: $APP_CONFIG"
echo "Classpath: $CLASSPATH"

# Start the application in background
java $JAVA_OPTS $SPRING_CONFIG $LOGBACK_CONFIG -cp "$CLASSPATH" com.io.infracloud.urlshortener.URLShortenerApplication &

# Store the Java process ID
JAVA_PID=$!

echo "Application started with PID: $JAVA_PID"
echo "Use 'docker stop <container_name>' to gracefully stop the application"

# Wait for the Java process to complete
wait $JAVA_PID

echo "Application exited"