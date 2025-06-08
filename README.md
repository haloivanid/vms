# VMS - Vacancy Management System

## 1. Overview

VMS is a comprehensive Vacancy Management System built with Spring Boot. It is designed to manage job vacancies and candidates, with a sophisticated system for ranking candidates based on customizable criteria for each vacancy. The project leverages a Hexagonal Architecture (also known as Ports and Adapters) to ensure a clean separation of concerns, making it highly maintainable and testable.

## 2. Features

- **Candidate Management**: Full CRUD (Create, Read, Update, Delete) operations for candidates.
- **Vacancy Management**: Full CRUD operations for job vacancies.
- **Dynamic Criteria-Based Ranking**:
  - Define multiple weighted criteria for each vacancy (e.g., age, gender, salary expectations).
  - Automatically calculate a score for each candidate based on how well they match the vacancy's criteria.
  - Retrieve a paginated list of candidates ranked by their score for a specific vacancy.
- **RESTful API**: A well-defined REST API for interacting with the system.
- **Pagination and Sorting**: Support for pagination and sorting on lists of candidates and vacancies.
- **Centralized Exception Handling**: Consistent error response format for all API endpoints.
- **Standardized API Response**: All successful responses are wrapped in a consistent JSON structure.

## 3. Architecture

This project implements the **Hexagonal Architecture (Ports and Adapters)**. This architectural style isolates the core business logic from external concerns like databases, web frameworks, and other services. This separation makes the application easier to test, maintain, and evolve.

The project structure is organized as follows:

- **`domain`**: This is the core of the application, containing the business models, logic, and ports (interfaces). It has no dependencies on any other layer.

  - **`model`**: Contains the domain entities like `Candidate`, `Vacancy`, and `Criteria`.
  - **`port.in`**: Defines the "incoming" ports (use cases), which are implemented by the application services (e.g., `CandidateUseCase`, `VacancyUseCase`).
  - **`port.out`**: Defines the "outgoing" ports for data persistence, which are implemented by the persistence adapters (e.g., `CandidateRepositoryPort`, `VacancyRepositoryPort`).

- **`application`**: This layer contains the application services that orchestrate the domain logic. It implements the "in" ports from the domain layer.

  - Example: `CandidateService` implements `CandidateUseCase`, and `VacancyService` implements `VacancyUseCase`.

- **`adapter`**: This layer contains the adapters that interact with the outside world. They are the "plugs" for the ports defined in the domain.
  - **`in/web`**: The "incoming" adapter for the web layer. It contains the Spring MVC controllers (`CandidateController`, `VacancyController`) that handle HTTP requests and translate them into calls to the application services.
  - **`out/persistence`**: The "outgoing" adapter for data persistence. It contains the implementation of the repository ports using Spring Data MongoDB (`CandidateRepositoryAdapter`, `VacancyRepositoryAdapter`).

## 4. Technologies Used

- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Web**: For building the RESTful API.
- **Spring Data MongoDB**: For data persistence with MongoDB.
- **Spring Validation**: For request validation.
- **Lombok**: To reduce boilerplate code.
- **Maven**: For dependency management and project build.

## 5. API Endpoints

The API is documented in the provided Postman collection. You can import the `postman.json` file to get started.

### 5.1. Candidate Endpoints

- `POST /api/v1/candidates`: Create a new candidate.
- `GET /api/v1/candidates`: Get a paginated list of all candidates.
- `GET /api/v1/candidates/{id}`: Get a specific candidate by their ID.
- `PUT /api/v1/candidates/{id}`: Update an existing candidate.
- `DELETE /api/v1/candidates/{id}`: Delete a candidate.

### 5.2. Vacancy Endpoints

- `POST /api/v1/vacancies`: Create a new vacancy with its criteria.
- `GET /api/v1/vacancies`: Get a paginated list of all vacancies.
- `GET /api/v1/vacancies/{id}`: Get a specific vacancy by its ID.
- `PUT /api/v1/vacancies/{id}`: Update an existing vacancy and its criteria.
- `DELETE /api/v1/vacancies/{id}`: Delete a vacancy.
- `GET /api/v1/vacancies/{id}/ranks`: Get a paginated and ranked list of candidates for a specific vacancy.

## 6. Setup and Running the Project

### 6.1. Prerequisites

- **JDK 21**
- **Maven 3.x**
- **MongoDB**: The application is configured to connect to a local MongoDB instance.
- **Postman**: For testing the API endpoints.

### 6.2. Configuration

1.  **Database Connection**: The MongoDB connection details are configured in `src/main/resources/application.properties`.

    ```properties
    spring.data.mongodb.uri=mongodb://${MONGO_DB_USER:admin}:${MONGO_DB_PASSWORD:password}@127.0.0.1:27017/vms?authSource=admin&authMechanism=SCRAM-SHA-256
    ```

    By default, it connects to a local MongoDB on port `27017` with the username `admin` and password `password`. You can change these values or set the `MONGO_DB_USER` and `MONGO_DB_PASSWORD` environment variables.

2.  **Property Naming Strategy**: The application uses `SNAKE_CASE` for JSON properties, as configured in `application.properties`.
    ```properties
    spring.jackson.property-naming-strategy=SNAKE_CASE
    ```

### 6.3. Running the Application

1.  **Clone the repository**.
2.  **Navigate to the project root directory**.
3.  **Build the project using Maven**:
    ```bash
    mvn clean install
    ```
4.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, you can run the application from your IDE by running the `main` method in the `VmsApplication` class.

The application will start on the default port `8080`.

## 7. How to Use

1.  **Import the Postman Collection**: Import the `postman.json` file into your Postman client. This will create a new collection named "VMS Project API".
2.  **Set Base URL**: The collection uses a variable `{{base_url}}` which is pre-configured to `http://localhost:8080`.
3.  **Execute Requests**:
    - Start by using the "Create Candidate" request to add a few candidates to the database.
    - Then, use the "Create Vacancy" request to create a job vacancy.
    - Finally, use the "Get Ranks for Vacancy" request, making sure to set the `vacancy_id` in the URL, to see the ranked list of candidates. The collection is configured to automatically set the `candidate_id` and `vacancy_id` variables when you create new entries.
