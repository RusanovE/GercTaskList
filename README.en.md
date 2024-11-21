# GercTaskList

## Description of the system

**Task List** is a task management system that allows users to track and manage their status. The system provides two roles: **User** and **Administrator**. Each role has its own functions and access:

### Functions for users:
- **Registration** and **login** with the issuance of a JWT token.
- **Creating tasks** with a title, description, due date and priority.
- **Viewing and filtering tasks** by status (completed/not completed).
- **Editing** and **deleting** only your tasks.
- **Changing the task status** to completed.

### Functions for administrators:
#### User management:
- **Viewing a list of all users**.
- **Deleting users**.
- **Change user roles** (assign/remove administrator role).

#### Task management:
- **View all user tasks**.
- **Edit** and **delete** any tasks.

#### Statistics:
- **Number of completed and uncompleted tasks**.
- **User statistics**: number of tasks created by each user.

#### Export reports to CSV:
- **Export statistics** on tasks and user activity.

## Technical requirements:
- **Kotlin** for development.
- **Spring Boot** using **JPA** for working with the database (PostgreSQL).
- **JWT** for authentication.
- **Spring Security** for implementing role-based security and authorization.
- **OpenAPI (Swagger)** for documenting and testing APIs.
- **CSV** for data export.

## Installation and Run

### Requirements:
- **JDK 11** or higher.
- **PostgreSQL**.
- **Gradle**.

### 1. Clone the repository:
```bash
git clone https://github.com/your-repository.git
cd your-repository
```
### 2. Set up the database:
1. Install **PostgreSQL** and create a database:
   ```sql
    CREATE DATABASE name_db;

Update the `application.properties` file in the `src/main/resources` folder with your database connection settings:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tasklist
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.driver-class-name=org.postgresql.Driver
```

### 3. Running the application:

To run the application, use Gradle:

```bash
./gradlew bootRun
```
After a successful start, the application will be available on port 8080

### 4. Swagger API:

Documentation for all APIs is available via Swagger UI at the following address: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 5. Project structure

- `src/main/kotlin/org/example/gerctasklist` — the main source code of the application.
- `src/main/resources/application.properties` — application configuration.
- `src/test/kotlin/org/example/gerctasklist` — application tests.

### Dependencies

The project uses the following libraries:

- Spring Boot for creating RESTful applications.
- Spring Security for implementing authentication and authorization.
- JWT for API protection.
- OpenAPI (Swagger) for auto-generating API documentation.
- JPA/Hibernate for working with the PostgreSQL database.
- CSV for exporting statistics.
- JUnit for testing.