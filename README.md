# RhythmAPI
A robust RESTful API for managing a music catalog, built with Spring Boot and MongoDB. This application provides comprehensive features for managing albums, songs, and users with secure authentication and role-based access control.

## Features

### Album Management
- Complete CRUD operations for albums
- Advanced search and filtering capabilities
- Pagination support for large datasets
- Sorting options for flexible data presentation

### Song Management
- Full CRUD operations for songs
- List songs by album
- Search functionality by title
- Pagination and sorting capabilities

### User Management
- Secure user authentication
- Role-based access control (USER/ADMIN)
- User profile management
- Password encryption and security

### Security Features
- JWT-based stateless authentication
- Role-based authorization
- Secure endpoint protection
- Token-based session management

## Technical Stack

### Backend Framework
- **Spring Boot** - Primary application framework
- **Spring Security** - Authentication and authorization
- **Spring Data MongoDB** - Database operations
- **JWT (jjwt)** - JWT token handling

### Database
- **MongoDB** - NoSQL database for flexible data storage

### DevOps & Deployment
- **Docker** - Application containerization
- **DockerHub** - Container registry
- **Jenkins** - CI/CD pipeline automation

## Getting Started

### Prerequisites
- Java 17 or higher
- MongoDB 4.4+
- Docker
- Maven 3.6+

### Environment Setup

1. Clone the repository:
```bash
git clone https://github.com/JavaAura/HACHAMI_MED_S4_B2_RhythmAPI
cd 
```

2. Configure environment variables:
```properties
MONGODB_URI=your_mongodb_connection_string
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=3600000
```

3. Build the application:
```bash
mvn clean package
```

### Running Locally

#### Using Maven
```bash
mvn spring-boot:run
```

#### Using Docker
```bash
# Build Docker image
docker build -t music-catalog-api .

# Run container
docker run -p 8080:8080 music-catalog-api
```

## API Documentation

### Authentication Endpoints

```
POST /api/auth/register
POST /api/auth/login
```

### Album Endpoints

```
GET    /api/albums
POST   /api/albums
GET    /api/albums/{id}
PUT    /api/albums/{id}
DELETE /api/albums/{id}
```

### Song Endpoints

```
GET    /api/songs
POST   /api/songs
GET    /api/songs/{id}
PUT    /api/songs/{id}
DELETE /api/songs/{id}
GET    /api/albums/{albumId}/songs
```

### User Endpoints

```
GET    /api/users
PUT /api/admin/roles/{username}   
```

## Security Implementation

### JWT Authentication Flow
1. Client submits credentials
2. Server validates and returns JWT
3. Client includes JWT in subsequent requests
4. Server validates token for each protected endpoint

### Role-Based Access Control
- **USER**: Basic operations on albums and songs
- **ADMIN**: Full access to all endpoints including user management

## Exception Handling

The API implements a centralized exception handling system using `@ControllerAdvice`:

- Custom business exceptions
- Validation errors
- Authentication/Authorization errors
- Database operation errors

## Data Validation

Comprehensive validation using Bean Validation:

- `@NotNull`, `@NotBlank`, `@Size` annotations
- Custom validators for complex business rules
- Request payload validation
- Response data sanitization

## DevOps Integration

### CI/CD Pipeline with Jenkins

1. Source code checkout
2. Run tests
3. Build application
4. Create Docker image
5. Push to DockerHub
6. Deploy to target environment

### Docker Configuration

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
