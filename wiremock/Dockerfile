# Dockerfile for WireMock standalone server
# Usage: docker run -d -p 8081:8081 clinic-wiremock
FROM wiremock/wiremock:latest

# Set working directory
WORKDIR /home/wiremock

# Copy mapping files
COPY mappings ./mappings

# Copy response files
COPY __files ./__files

# Expose the WireMock port
EXPOSE 8081

# Run WireMock in standalone mode on port 8081
CMD ["--port", "8081", "--verbose"]
