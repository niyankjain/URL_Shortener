FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the thin JAR
COPY build/libs/url-shortener-1.0.0-plain.jar url-shortener-1.0.0.jar

# Copy dependencies
COPY build/libs/lib lib/

# Copy config files
COPY build/libs/config/application.yaml config/
COPY build/libs/config/logback.xml config/

# Copy run script
COPY build/libs/config/run.sh run.sh

# Make run script executable
RUN chmod +x run.sh

# Create logs directory
RUN mkdir -p logs

# Expose port
EXPOSE 9091

# Use run.sh as entrypoint
ENTRYPOINT ["./run.sh"]