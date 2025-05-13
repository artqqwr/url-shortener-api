# Java Spring URL Shortener Pet Project

## Description

This is a simple pet project web application built with Java and Spring Boot for shortening URLs.

## Features

- Auth (jwt based)
- Secure endpoint for URL shortening (accessible to authenticated users)
- Public redirection from a short code to the original URL
- Asynchronous logging of click statistics including IP address, User-Agent, and Referrer
- Pre-generated pool of unique short codes with asynchronous auto-refill
- Caching of frequent URL mappings using Redis
- Automatic API documentation built with Swagger/OpenAPI
- Containerized database (PostgreSQL) and cache (Redis) via Docker Compose

![image](https://github.com/user-attachments/assets/bd7cdfce-5497-4b1a-9dbb-25f3a6108b9f)


## Technologies Used

- **Java 11+**
- **Spring Boot**
  - Spring Data JPA (with PostgreSQL)
  - Spring Security (with JWT)
  - Spring Validation
  - Spring Cache (with Redis)
  - Springdoc OpenAPI for Swagger documentation
- **Lombok** (to reduce boilerplate)
- **PostgreSQL**
- **Redis**
- **Gradle** (for build management)
- **Docker & Docker Compose**

## Prerequisites

- [Java JDK 11+](https://openjdk.java.net/)
- [Gradle](https://gradle.org/)
- [Docker](https://www.docker.com/) 

## Getting Started

1. **Clone the repository:**

   ```bash
   git clone https://github.com/artqqwr/url-shortener-api.git
   cd url-shortener-api
   ```

2. **Configure environment variables:**

   Create an `application.properties` file in `src/main/resources` (or update it accordingly) with the following settings:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   
   spring.jpa.hibernate.ddl-auto=update

   spring.cache.type=redis
   spring.redis.host=localhost
   spring.redis.port=6379

   app.jwtSecret=YourSuperSecretKeyThatIsAtLeast64CharactersLong12345678!!!
   app.jwtExpirationMs=86400000

   springdoc.swagger-ui.path=/swagger-ui/index.html
   ```

3. **Build and run the application:**

   To build your application with Gradle, run:

   ```bash
   ./gradlew build
   ```

   Then start the application:

   ```bash
   ./gradlew bootRun
   ```

4. **Start the Database and Cache using Docker Compose:**

   Make sure you have Docker installed and then, in the project root, run:

   ```bash
   docker-compose up -d
   ```

   This will launch PostgreSQL and Redis containers as described in the `docker-compose.yml` file.

## Usage

- **Swagger UI:**  
  After starting the application, open your browser to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) to access the automatically generated API documentation.

- **User Endpoints:**
  - **Registration:** `POST /api/register`
  - **Authentication:** `POST /api/authenticate` (returns a JWT token)

- **URL Shortening:**
  - **Shorten URL:** `POST /api/shorten` (requires authentication; provide a valid original URL)
  - **Redirection:** `GET /{shortCode}`  
    Visiting this URL will redirect to the original URL and asynchronously log click details.

## Project Structure

```plaintext
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── artqqwr
│   │   │           └── urlshortener
│   │   │               ├── config
│   │   │               ├── controller
│   │   │               ├── dto
│   │   │               ├── entity
│   │   │               ├── event
│   │   │               ├── repository
│   │   │               ├── security
│   │   │               └── service
│   │   └── resources
│   └── test
├── docker-compose.yml
├── build.gradle
└── README.md
```

## TODO

- [X] Auth
- [X] Docker
- [X] Url shortening and public redir
- [X] Redis caching for url mappings
- [X] Asynchronous auto-refill for short code pool
- [X] Click statistics logging (have TODO)
- [X] Swagger doc
- [X] Postgres
- [ ] Additional enhancements and tests

