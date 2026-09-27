# University Schedule App

A web-based university schedule management system built with **Java and Spring Boot**. The application provides role-based access for university staff and allows managing students, teachers, courses, classrooms, and academic schedules.

The project demonstrates a layered Spring architecture, role-based authorization, database persistence, validation, transaction management, and server-side rendering with Thymeleaf.

## Features

* Role-based access control for different types of users
* Authentication using email and password
* Separate functionality for:

  * **ADMIN** — manages users, courses, classrooms, and schedules
  * **TEACHER** — views and manages assigned teaching schedules
  * **STUDENT** — views their academic schedule
  * **STAFF** — manages academic schedule data
* Course management
* Teacher management
* Student management
* Classroom management
* Schedule management
* Schedule filtering and viewing
* Server-side form validation
* Persistent data storage in PostgreSQL
* Database schema versioning with Flyway
* Transaction management with Spring `@Transactional`
* Password hashing with Spring Security
* MVC architecture with Thymeleaf templates
* Unit and integration testing

## Tech Stack

### Backend

* **Java 17**
* **Spring Boot**
* **Spring MVC**
* **Spring Security**
* **Spring Data JPA**
* **Hibernate**
* **Bean Validation**
* **Thymeleaf**

### Database

* **PostgreSQL**
* **Flyway**

### Testing

* **JUnit 5**
* **Mockito**
* **Spring Boot Test**
* **MockMvc**

### Build & Development

* **Maven**
* **Git**
* **IntelliJ IDEA**

## Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Main layers

**Controller**

Handles HTTP requests, validates input, prepares model attributes, and selects Thymeleaf views.

**Service**

Contains business logic and transaction boundaries. Service interfaces are used to separate business contracts from implementations.

**Repository**

Provides database access through Spring Data JPA.

**Model**

Contains JPA entities representing the application's domain.

**Security**

Contains authentication, authorization, password encoding, and custom user details handling.

**View**

Thymeleaf templates are used for server-side HTML rendering.

## Domain Model

The main entities include:

* `User`
* `Student`
* `Teacher`
* `Course`
* `Schedule`
* `Classroom`

A schedule connects the main academic entities:

```text
Teacher
   │
   ├──────────┐
   │          │
   ▼          ▼
Course ←── Schedule ──→ Classroom
```

The `Schedule` entity stores information such as:

* course
* teacher
* classroom
* date
* time slot

## Security

The application uses **Spring Security** for authentication and authorization.

Users are assigned one of the following roles:

```text
ROLE_ADMIN
ROLE_STUDENT
ROLE_TEACHER
ROLE_STAFF
```

Access to application resources is restricted according to the authenticated user's role.

Passwords are stored using a secure password encoder rather than plain text.

## Database

The application uses **PostgreSQL** as the primary relational database.

Database schema changes are managed with **Flyway migrations**.

Example migration structure:

```text
src/main/resources/db/migration/
├── V1__create_users_table.sql
├── V2__create_students_table.sql
├── V3__create_teachers_table.sql
├── V4__create_courses_table.sql
├── V5__create_classrooms_table.sql
└── V6__create_schedules_table.sql
```

Flyway ensures that database changes are versioned and applied consistently across environments.

## Transaction Management

Business operations are executed inside Spring-managed transactions.

Service methods use:

```java
@Transactional
```

This ensures that operations modifying related entities are executed atomically and database changes are properly committed or rolled back when an error occurs.

## Validation

The application uses Jakarta Bean Validation for validating user input.

Typical validation constraints include:

```java
@NotBlank
@Email
@Size
@NotNull
```

Validation errors are handled at the MVC layer and displayed directly in Thymeleaf forms.

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── ua/
│   │       └── foxminded/
│   │           └── scheduleapp/
│   │               ├── config/
│   │               ├── controller/
│   │               ├── model/
│   │               ├── repository/
│   │               ├── service/
│   │               │   └── impl/
│   │               └── ScheduleAppApplication.java
│   │
│   └── resources/
│       ├── db/
│       │   └── migration/
│       ├── static/
│       │   ├── css/
│       │   └── js/
│       ├── templates/
│       │   ├── admin/
│       │   ├── student/
│       │   ├── teacher/
│       │   └── ...
│       └── application.properties
│
└── test/
    └── java/
        └── ua/
            └── foxminded/
                └── scheduleapp/
```

## Configuration

Create a PostgreSQL database before starting the application.

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/scheduleapp
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=validate

spring.flyway.enabled=true
```

It is recommended to keep credentials outside the source code when deploying the application.

For example, environment variables can be used:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

## Running the Application

### Prerequisites

Make sure the following are installed:

* Java 17+
* Maven 3.8+
* PostgreSQL 14+
* Git

### Clone the repository

```bash
git clone https://github.com/your-username/university-schedule-app.git
cd university-schedule-app
```

### Configure PostgreSQL

Create the database:

```sql
CREATE DATABASE scheduleapp;
```

Configure your database credentials in `application.properties` or through environment variables.

### Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

## Testing

Run the complete test suite:

```bash
./mvnw test
```

The project uses:

* **JUnit 5** for unit and integration tests
* **Mockito** for mocking dependencies
* **Spring Boot Test** for application context testing
* **MockMvc** for testing MVC controllers

Tests cover service-layer business logic, controller behavior, validation, and repository interactions.

## Development Principles

The project follows several common Java/Spring development practices:

* Layered architecture
* Separation of concerns
* Dependency Injection
* Interface-based service design
* DTO-oriented request handling where appropriate
* Declarative transaction management
* Repository abstraction through Spring Data JPA
* Role-based authorization
* Server-side validation
* Database migration versioning
* Unit and integration testing
* Clean and maintainable code
* Avoiding unnecessary duplication

## Error Handling

The application handles common application and validation errors at the MVC layer.

Typical scenarios include:

* Invalid form input
* Missing entities
* Unauthorized access
* Invalid authentication credentials
* Database-related failures

Validation errors are returned to the corresponding Thymeleaf forms so users can correct their input.

## Example Workflow

A typical schedule management workflow:

```text
User Login
    ↓
Spring Security Authentication
    ↓
Role Verification
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
    ↓
Thymeleaf View
```

For example, when a teacher opens their schedule:

```text
GET /teacher/schedule
        ↓
TeacherController
        ↓
ScheduleService
        ↓
ScheduleRepository
        ↓
PostgreSQL
        ↓
List<Schedule>
        ↓
teacher/schedule.html
```

## Future Improvements

Possible future improvements include:

* REST API for schedule management
* Pagination and advanced filtering
* Schedule conflict detection
* Calendar-based schedule view
* Email notifications
* Import/export of schedules
* Docker-based deployment
* CI/CD pipeline
* API documentation with OpenAPI / Swagger
* Integration tests using Testcontainers
* Audit logging
* Internationalization

## Project Purpose

This project was created as a practical **Java/Spring backend project** to demonstrate experience with:

* Spring Boot application development
* Spring Security
* Spring Data JPA and Hibernate
* PostgreSQL database design
* Flyway database migrations
* Transaction management
* MVC architecture
* Thymeleaf
* Role-based access control
* Automated testing
* Clean and maintainable backend architecture

## License

This project is intended for educational and portfolio purposes.
