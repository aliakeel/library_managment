# Library Management System using Spring Boot

## Intro
The Library Management System API is built using Spring Boot, allowing librarians to manage books, patrons, and borrowing records.

## Entities:

### Book:
Attributes: ID, title, author, publication year, ISBN, etc.
Implementation: Book.java
### Patron:
Attributes: ID, name, contact information, etc.
Implementation: Patron.java
### Borrowing Record:
Attributes: ID, book, patron, borrowing date, return date.
Implementation: BorrowingRecord.java
### API Endpoints:
#### Book Management Endpoints:

GET /api/books/all: Retrieve a list of all books.
GET /api/books: Retrieve a list of all books with filter (title, author) and pagination
GET /api/books/{id}: Retrieve details of a specific book by ID.
GET /api/books/available: Retrieve a list of all available books for borrowing.
POST /api/books: Add a new book to the library.
PUT /api/books/{id}: Update an existing book's information.
DELETE /api/books/{id}: Remove a book from the library.
#### Patron Management Endpoints:

GET /api/patrons/all: Retrieve a list of all patrons.
GET /api/patrons: Retrieve a list of all patrons with filter (name) and pagination.
GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
POST /api/patrons: Add a new patron to the system.
PUT /api/patrons/{id}: Update an existing patron's information.
DELETE /api/patrons/{id}: Remove a patron from the system.
#### Borrowing Endpoints:
GET /api/borrowings: Retrieve a list of all borrowing records with filter (patron name, book title) and pagination.
GET /api/borrowings/all: Retrieve a list of all borrowing records.
GET /api/borrowings/{id}: Retrieve details of a specific borrowing record by ID.
POST /api/borrowings: Add a new borrowing record to the system.
POST /api/borrowings/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.
PUT /api/borrowings/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.
PUT /api/borrowings/{id}: Record the return of a borrowed book by a patron.
DELETE /api/borrowings/{id}: Remove a borrowing record from the system.
### Data Storage:
#### Database: MySQL
Relationships: One-to-many between books and borrowing records, patrons and borrowing records.

### Running the Application:
Clone the repository from GitHub: 
```
git clone https://github.com/aliakeel/library_managment.git
```
Configure the MySQL database in application.properties.
Run the application using ./gradlew bootRun.
Access Swagger UI at http://localhost:8910/swagger-ui/index.html for API documentation and testing.

### Validation and Error Handling:
Implemented input validation for API requests.
Handled exceptions gracefully, returning appropriate HTTP status codes and error messages.
Security:
Implemented JWT-based authorization to protect the API endpoints.
### Aspects:
Logging using Aspect-Oriented Programming (AOP) to log method calls, exceptions, and performance metrics for certain operations.
### Caching:
Utilized Spring's caching mechanisms to cache frequently accessed data, such as book details or patron information, to improve system performance.
### Transaction Management:
Implemented declarative transaction management using Spring's @Transactional annotation to ensure data integrity during critical operations.
### Testing:
Unit tests for validating API endpoints using JUnit and Mockito.
tests are provided for:
```
BookControllerTest.java
PatronControllerTest.java
BorrowingRecordControllerTest.java
```

### Architecture:
##### Controller Layer:

Contains RESTful endpoints for managing books, patrons, and borrowing records.
Example: BookController.java, PatronController.java, BorrowingRecordController.java
##### Service Layer:

Contains business logic for the application.
Example: BookService.java, PatronService.java, BorrowingRecordService.java
##### Repository Layer:

Interfaces for data access, extending Spring Data JPA repositories.
Example: BookJpaRepository.java, PatronJpaRepository.java, BorrowingRecordJpaRepository.java
##### Security Layer:

Configures JWT-based authentication and authorization.
Example: JwtAuthenticationEntryPoint.java, JwtRequestFilter.java, JwtTokenUtil.java, SecurityConfig.java
##### Aspect Layer:

Handles logging using AOP.
Example: LoggingAspect.java
##### Caching Configuration:

Configures caching for frequently accessed data.
Example: CacheConfig.java
##### Transactional Management:

Ensures data integrity using Spring's @Transactional annotation.
### Evaluation Criteria:
##### Functionality: Verified CRUD operations for books, patrons, and borrowing records.
##### Code Quality: Ensured code readability, maintainability, and adherence to best practices.
##### Error Handling: Implemented proper handling of edge cases and validation errors.
##### Testing: Assessed coverage and effectiveness of unit tests.
##### Bonus Features: Included authorization, transaction management, caching, and aspects.