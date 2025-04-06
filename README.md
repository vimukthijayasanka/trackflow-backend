# 💰 Expense Tracker API

A simple RESTful API for tracking income and expenses, built with **Spring Boot**. This project is designed to demonstrate full CRUD operations and user authentication functionality, suitable for integration with a frontend like Angular.

---

## 🚀 Features

- 🔐 User authentication (login/logout)
- ➕ Add new income/expense records
- 📄 Fetch all or individual records
- ♻️ Update existing transactions
- ❌ Delete income/expense entries
- 📦 RESTful API structure with JSON payloads

---

## 🛠️ Tech Stack

- Java 22+
- Spring Web MVC 3.2.5
- Maven
- POSTGRESQL database
- Postman for testing

---

## 📦 Folder Structure

```
src/
├── main/
│   ├── java/
│   │   └── lk/ijse/dep13/backendexpensemanager/
│   │       ├── api/               # REST controllers
│   │       ├── model/             # Entity classes (User, IncomeExpense)
│   │       ├── service/           # Business logic layer (to be added)
│   │       ├── repository/        # Data access layer (to be added)
│   │       ├── WebAppConfig.java  # Spring configuration
│   │       ├── WebAppInitializer.java
│   │       └── WebRootConfig.java
│   ├── resources/
│   │   └── webapp/
│   │       └── WEB-INF/
├── test/                          # Unit & integration tests
└── target/                        # Compiled bytecode & build artifacts
```

---

## 📮 API Endpoints

### 🔐 Auth

| Method | Endpoint              | Description        |
|--------|-----------------------|--------------------|
| POST   | `/auth/login`         | Log in             |
| DELETE | `/auth/logout`        | Log out            |

### 💵 Transactions

| Method | Endpoint            | Description                |
|--------|---------------------|----------------------------|
| POST   | `/transactions`     | Create a new record        |
| GET    | `/transactions`     | Get all records            |
| GET    | `/transactions/{id}` | Get one record by ID       |
| PATCH  | `/transactions/{id}` | Update a record by ID      |
| DELETE | `/transactions/{id}` | Delete a record by ID      |

---

## 🧾 Sample Request (POST /api/transactions)

```json
{
  "id": "tx123",
  "type": "expense",
  "description": "food",
  "amount": 1500.00,
  "date": "2025-04-06",
  "time" : "18.05.45"
}
```

---

## 🧑‍💻 Getting Started

### 📦 Prerequisites

- JDK 22+
- Maven
- IntelliJ IDEA / VSCode
- Postman (for testing)

### 🛠️ Run the App

1. Clone the repository:

```bash
git clone https://github.com/your-username/expense-tracker-api.git
cd expense-tracker-api
```

2. Run with Maven:

```bash
mvn spring-boot:run
```

3. App will be available at:

```
http://localhost:8080
```

---

## ✅ To Do

- [ ] Connect to POSTGRESQL
- [ ] Implement JWT authentication
- [ ] Create unit tests

---

## 👨‍🏫 Author

- 🧑‍💻 Developed by: Vimukthi Jaysanka
- 📧 Email: jayasanka1997@gmail.com

---

## 🪪 License

This project is licensed under the [MIT License](license.txt).
