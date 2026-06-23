# Sample CRUD App - Quick Setup & Test Instructions

## Project Structure

```
sample-crud-app/
├── src/main/java/com/example/crud/
│   ├── SampleCrudApplication.java          (Main Spring Boot app)
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── ProductController.java
│   │   └── OrderController.java
│   ├── service/
│   │   ├── UserService.java                (In-memory list storage)
│   │   ├── ProductService.java
│   │   └── OrderService.java
│   ├── model/
│   │   ├── User.java
│   │   ├── Product.java
│   │   └── Order.java
│   └── dto/
│       ├── UserDto.java
│       ├── ProductDto.java
│       └── OrderDto.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
├── README.md
└── TESTING_GUIDE.md
```

## Quick Start

### 1. Build the Application

```bash
cd sample-crud-app
mvn clean install
```

Expected output: `BUILD SUCCESS`

### 2. Start the Server

```bash
mvn spring-boot:run
```

Expected output: 
```
Started SampleCrudApplication in X.XXX seconds
```

Server runs on: `http://localhost:8081`

### 3. Verify It's Running

```bash
curl http://localhost:8081/api/users
```

Expected response:
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "ADMIN",
    "createdAt": "2026-06-23T10:30:00"
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane@example.com",
    "role": "USER",
    "createdAt": "2026-06-23T10:30:00"
  }
]
```

---

## Testing with TestGen - 3 Easy Steps

### Step 1: Initialize Git Repository

```bash
cd sample-crud-app
git init
git add .
git commit -m "Initial commit: Sample CRUD app"
```

### Step 2: Create a Feature Branch

```bash
git checkout -b feature/test-api
```

Make some changes (e.g., add a new endpoint), then commit:
```bash
git add .
git commit -m "Add new API endpoint"
```

### Step 3: Use TestGen Compare Branches

1. Open TestGen React UI (http://localhost:3000)
2. Click **"Compare Branches"** tab
3. Fill in the form:
   - **Repository URL**: `file:///d:/AI/AI_test_gen/sample-crud-app` (local file path)
   - **Base Branch**: `main`
   - **Head Branch**: `feature/test-api`
   - **Language**: `java`
   - **Framework**: `JUnit 5`
4. Click **"Compare & Generate Tests"**

TestGen will:
- ✅ Fetch the diff between branches
- ✅ Filter to API-relevant files
- ✅ Generate tests for changed endpoints

---

## API Endpoints Summary

### Users (`/api/users`) - 6 endpoints
- `GET /api/users` - List all
- `GET /api/users/{id}` - Get by ID
- `POST /api/users` - Create
- `PUT /api/users/{id}` - Update
- `DELETE /api/users/{id}` - Delete
- `GET /api/users/count` - Count total

### Products (`/api/products`) - 7 endpoints
- `GET /api/products` - List all
- `GET /api/products/{id}` - Get by ID
- `GET /api/products/category/{category}` - Filter by category
- `POST /api/products` - Create
- `PUT /api/products/{id}` - Update
- `DELETE /api/products/{id}` - Delete
- `GET /api/products/count` - Count total

### Orders (`/api/orders`) - 8 endpoints
- `GET /api/orders` - List all
- `GET /api/orders/{id}` - Get by ID
- `GET /api/orders/user/{userId}` - Filter by user
- `GET /api/orders/status/{status}` - Filter by status
- `POST /api/orders` - Create
- `PUT /api/orders/{id}` - Update
- `DELETE /api/orders/{id}` - Delete
- `GET /api/orders/count` - Count total

**Total: 21 REST endpoints across 3 resources**

---

## Sample Test Scenarios (See TESTING_GUIDE.md)

### Scenario 1: Product Search Feature
- Add search capability to products
- TestGen generates tests for new `/products/search` endpoint

### Scenario 2: Order Status Tracking
- Add status update endpoints to orders
- TestGen generates tests for new `/orders/status` endpoints

### Scenario 3: User Admin Functions
- Add admin endpoints for user management
- TestGen generates tests for new admin endpoints

---

## Data Storage

All data is stored **in-memory** using Java `ArrayList`:

```
UserService.users → ArrayList<User>
ProductService.products → ArrayList<Product>
OrderService.orders → ArrayList<Order>
```

**Benefits**:
- ✅ No database setup needed
- ✅ Fast startup
- ✅ Perfect for testing
- ✅ Data resets on server restart

---

## Technology Stack

| Component | Version |
|-----------|---------|
| Java | 17 |
| Spring Boot | 3.2.3 |
| Maven | 3.x |
| REST API | Spring Web |

---

## Troubleshooting

### Build Fails
```bash
# Clean and rebuild
mvn clean
mvn install
```

### Port Already in Use
The app runs on port `8081`. If busy, edit `application.properties`:
```properties
server.port=8082
```

### Git Not Found
Ensure Git is installed and added to PATH:
```bash
git --version
```

### TestGen Not Detecting Changes
1. Ensure Git is properly initialized
2. Commit changes to the feature branch
3. Use absolute file paths in TestGen form
4. Check that changed files are in `/controller/` or `/service/` directories

---

## Next Steps

1. ✅ Build and run the app
2. ✅ Test with curl to verify endpoints work
3. ✅ Initialize Git and create branches
4. ✅ Use TestGen Compare Branches feature
5. ✅ Review generated test cases
6. ✅ Try the scenarios in TESTING_GUIDE.md

---

## Need Help?

1. **Build issues?** → Check Maven installation
2. **Port conflicts?** → Change server.port in application.properties
3. **Git issues?** → See TESTING_GUIDE.md for Git setup
4. **TestGen issues?** → Check QUICK_REFERENCE.md in testgen-backend

---

**Ready to test?** Let's go! 🚀

```bash
cd sample-crud-app && mvn spring-boot:run
```
