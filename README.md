# Sample CRUD Application

In-memory CRUD application for testing the GitHub Branch Comparison feature with TestGen.

## Features

- **User Management**: Create, read, update, delete users with roles
- **Product Management**: Create, read, update, delete products with categories
- **Order Management**: Create, read, update, delete orders with status tracking
- **In-Memory Storage**: All data stored in Java ArrayLists (no database required)
- **REST API**: Full RESTful endpoints for all operations

## Endpoints

### Users (`/api/users`)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/count` - Count total users
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Products (`/api/products`)
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/count` - Count total products
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Orders (`/api/orders`)
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/user/{userId}` - Get orders by user
- `GET /api/orders/status/{status}` - Get orders by status
- `GET /api/orders/count` - Count total orders
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}` - Update order
- `DELETE /api/orders/{id}` - Delete order

## Building

```bash
mvn clean install
```

## Running

```bash
mvn spring-boot:run
```

Server will run on `http://localhost:8081`

## Testing with TestGen

1. Push this code to GitHub in multiple branches (e.g., `main`, `feature-products`, `feature-orders`)
2. Use the TestGen "Compare Branches" feature to:
   - Compare `main` vs `feature-products` to detect Product API changes
   - Compare `main` vs `feature-orders` to detect Order API changes
3. TestGen will generate focused test cases for only the changed endpoints

## Sample Data

### Initial Users
- ID: 1, Name: John Doe, Email: john@example.com, Role: ADMIN
- ID: 2, Name: Jane Smith, Email: jane@example.com, Role: USER

### Initial Products
- (Empty on startup)

### Initial Orders
- (Empty on startup)

## Sample Request/Response

### Create a Product
```bash
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 1299.99,
    "quantity": 10,
    "category": "Electronics"
  }'
```

### Response
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 1299.99,
  "quantity": 10,
  "category": "Electronics"
}
```

## Architecture

```
SampleCrudApplication
├── controller/
│   ├── UserController
│   ├── ProductController
│   └── OrderController
├── service/
│   ├── UserService (in-memory list)
│   ├── ProductService (in-memory list)
│   └── OrderService (in-memory list)
├── model/
│   ├── User
│   ├── Product
│   └── Order
└── dto/
    ├── UserDto
    ├── ProductDto
    └── OrderDto
```

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- Maven 3.x
- REST API (no database)

## Use Cases for Branch Testing

1. **Feature Branch**: Add new endpoint for bulk order operations
2. **Enhancement Branch**: Add filtering/sorting to products
3. **Bugfix Branch**: Fix user email validation
4. **Maintenance Branch**: Update Product price calculation logic

Each branch modification will be detected by TestGen's diff comparison, and focused tests will be generated only for the changed APIs.
