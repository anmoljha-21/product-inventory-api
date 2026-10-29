# Product Inventory REST API

A Spring Boot REST API for managing products with PostgreSQL persistence,
JWT authentication, role-based access control, and Redis caching.

## Tech Stack

- Java 21
- Spring Boot
- Spring Security and JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Redis
- Maven
- Docker Compose
- JUnit integration tests
- Swagger / OpenAPI

## Features

- Product CRUD operations
- PostgreSQL persistence
- JWT-based registration and login
- ADMIN and CUSTOMER roles
- Request validation and exception handling
- Redis caching for product listings with a five-minute TTL
- Cache eviction after product mutations
- Docker Compose configuration for PostgreSQL and Redis
- Integration tests for authentication and customer-role assignment

## Prerequisites

- Java 21
- Docker Desktop
- Git

## Run Locally

Clone the repository:

    git clone https://github.com/anmoljha-21/product-inventory-api.git
    cd product-inventory-api

Start PostgreSQL and Redis:

    docker compose up -d

Set the JWT secret in your terminal. Use a suitable secret for your
local environment; never commit it to Git.

    export JWT_SECRET='REPLACE_WITH_YOUR_BASE64_ENCODED_SECRET'

Start the application:

    ./mvnw spring-boot:run

The API runs at http://localhost:8080.

## Authentication

Register a user:

    POST /api/auth/register

Log in:

    POST /api/auth/login

Use the returned Bearer token in the Authorization header for protected
endpoints.

New registrations receive the CUSTOMER role. Product write operations
require ADMIN privileges.

## Product Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | /api/products | List products |
| GET | /api/products/{id} | Get a product |
| POST | /api/products | Create a product |
| PUT | /api/products/{id} | Update a product |
| DELETE | /api/products/{id} | Delete a product |

Product creation, updating, and deletion require ADMIN authorization.
Product read access is available to authenticated ADMIN and CUSTOMER users.

## API Documentation

When the application is running, try:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Run Tests

    ./mvnw test

## Database Schema

The schema-only PostgreSQL dump is provided in `database/schema.sql`.

## Stop Dependencies

    docker compose down

To remove the local PostgreSQL data volume as well, use
`docker compose down -v`. This permanently deletes that local database data.

## Security Notes

The configured database credentials are for local development only.
Use environment-based credentials and a securely managed JWT secret
for any deployed environment.
