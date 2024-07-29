## Daily Expense Track App

### Overview

The Expense Sharing Application is designed to manage user-related and expense-related data. It allows users to add expenses and split them among different people based on three different methods: exact amounts, percentages, and equal splits. The application manages user details, validates inputs.

## Architecture

The application consists of an API Gateway for request routing, backend services for business logic, and a PostgreSQL database for data storage. The application uses Spring Boot for backend services and JPA/Hibernate for database interactions.

### Functional Requirements

#### User Management
- **Each user should have:**
  - `email`
  - `name`
  - `mobile number`

#### Expense Management
- **Users can add expenses.**
- **Expenses can be split using three methods:**
  1. **Equal**: Split equally among all participants.
  2. **Exact**: Specify the exact amount each participant owes.
  3. **Percentage**: Specify the percentage each participant owes (ensure percentages add up to 100%).

### Expense Calculation Examples
1. **Equal**:
   - Scenario: You go out from cab with 5 friends. The total bill is 5000. Each friend owes 1000.
2. **Exact**:
   - Scenario: You go shopping with 2 friends and pay 5000. Friend 1 owes 1000, Friend 2 owes 1500, and you owe 2500.
3. **Percentage**:
   - Scenario: You go to a trip with 2 friends. You owe 20%, Friend 1 owes 30%, and Friend 2 owes 50%. (100%)

## API Endpoints

### User Endpoints (/users)
- **Create user:** `/new-user`
- **Retrieve user details:** `/{id}`
- **Retrieve all users:** `/all-users`

### Expense Endpoints (/expenses)
- **Add expense:** `/new-expense`
- **Retrieve individual user expenses:** `/user-expense/{userId}`
- **Retrieve overall expenses:** `/all`

## Data Validation
- Validate user inputs.
- Ensure percentages in the percentage split method add up to 100%.

## Getting Started

### Prerequisites
- Java Development Kit (JDK)
- Maven
- PostgreSQL Database
- Spring Boot

### Setup

1. **Set up PostgreSQL Database**:
   - Create a new database for the application.
   - Configure database connection properties in `application.properties`.

2. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

3. **Build the Application**:
   - Using Maven:
     ```bash
     mvn clean install
     ```

4. **Run the Application**:
   - Using Maven:
     ```bash
     mvn spring-boot:run
     ```

5. **Access the Application**:
   - Open a web browser and navigate to `http://localhost:8080`.

### Conclusion

This documentation provides an overview of the Expense Sharing Application architecture, including detailed descriptions of each component and their interactions. The provided setup and installation instructions will help you get started with the project.
