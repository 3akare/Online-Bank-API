# Online Bank API
This is a RESTful API for an online banking system that allows users to create accounts, perform transactions, check balances, and retrieve bank statements.

## File Structure
```
bank/
│-- src/main/java/com/onlinebanking/bank
│   ├── config/          # Configuration files (Thymeleaf, Logs configuration)
│   ├── controllers/     # REST controllers
│   ├── dtos/            # Data Transfer Objects (DTOs)
│   ├── entity/          # Entity classes (JPA models)
│   ├── repository/      # Database repositories
│   ├── services/        # Business logic and services
│   ├── utilities/       # utilities
│-- src/main/resources/
│   ├── application.yml  # Configuration file
│-- pom.xml              # Maven dependencies
│-- README.md            # Documentation
|-- AUTHORS.md      # List of Contributors
|-- postman_collection.json # Test Endpoints with postman
```
## Environment Setup
Ensure you have the following environment set up:

- Java 17 installed
- Maven installed or an IDE like IntelliJ
- Load all dependencies before running the API
- The API runs on port **8080**
- Update the `application.yml` file with your MySQL database credentials:
    - Database name
    - MySQL password
    - MySQL should run on port **3306** (can be updated)

## Running the Application
1. Clone the repository.
2. Install dependencies using Maven:
   ```sh
   mvn clean install
   ```
3. Start the application:
   ```sh
   mvn spring-boot:run
   ```
4. The API will be available at `http://localhost:8080/`.

## Features
- Create a user account
- Credit an account
- Debit an account
- Transfer funds
- Check account balance
- Retrieve account name
- Generate a bank statement

## API Endpoints

### 1. Create User Account
**Endpoint:** `POST /api/v1/createAccount`

**Request Body:**
```json
{
  "firstName": "Ahmad",
  "lastName": "MacGyver",
  "otherName": "Vernie_Kilback",
  "gender": "Male",
  "address": "3486 Kaylah Stravenue",
  "stateOfOrigin": "Kwara",
  "dateOfBirth": "2024-04-02",
  "email": "Willy.Kris68@example.net",
  "phoneNumber": "531-585-4754",
  "alternativePhoneNumber": "973-583-5487"
}
```

### 2. Credit Account
**Endpoint:** `POST /api/v1/creditAccount`

**Request Body:**
```json
{
  "accountNumber": "1234567890",
  "amount": 8000
}
```

### 3. Debit Account
**Endpoint:** `POST /api/v1/debitAccount`

**Request Body:**
```json
{
  "accountNumber": "1234567890",
  "amount": 5000
}
```

### 4. Transfer Funds
**Endpoint:** `POST /api/v1/transfer`

**Request Body:**
```json
{
  "senderAccountNumber": "1234567890",
  "receiverAccountNumber": "0987654321",
  "amount": 4000
}
```

### 5. Check Balance
**Endpoint:** `GET /api/v1/balanceEnquiry`

**Request Body:**
```json
{
  "accountNumber": "1234567890"
}
```

### 6. Get Account Name
**Endpoint:** `GET /api/v1/accountNameEnquiry`

**Request Body:**
```json
{
  "accountNumber": "1234567890"
}
```

### 7. Generate Bank Statement
**Endpoint:** `GET /bankStatement`

**Request Body:**
```json
{
  "accountNumber": "1234567890",
  "startDate": "2024-01-01",
  "endDate": "2025-03-26"
}
```
## Note
### Email Service
- The email service is **turned off** in the property file.
- To enable it, update the property file and provide the email password.
- The email password can be obtained from the [Google Console](https://console.cloud.google.com/) for Gmail accounts.
- The email service runs asynchronously (on a different thread).

### Postman Collection
A Postman testing export file is available for testing the API endpoints. You can import this file into Postman to easily test the API requests without manually entering the details.

## License
This project is licensed under the MIT License.