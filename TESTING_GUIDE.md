# Testing Guide - Sample CRUD App with TestGen Branch Comparison

This guide shows how to use the Sample CRUD App with the TestGen "Compare Branches" feature.

## Scenario 1: Create Feature Branch - Add Product Search

### Setup

```bash
cd d:\AI\AI_test_gen\sample-crud-app
git init
git add .
git commit -m "Initial commit: Basic CRUD app"
```

### Create Feature Branch

```bash
git checkout -b feature/product-search
```

### Modify ProductService to Add Search

Edit `src/main/java/com/example/crud/service/ProductService.java`:

Add this method:
```java
public List<Product> searchByName(String keyword) {
    return products.stream()
            .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(keyword.toLowerCase()))
            .toList();
}
```

### Modify ProductController to Add Endpoint

Edit `src/main/java/com/example/crud/controller/ProductController.java`:

Add this endpoint:
```java
@GetMapping("/search")
public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
    return ResponseEntity.ok(productService.searchByName(keyword));
}
```

### Commit Changes

```bash
git add -A
git commit -m "Add product search functionality"
```

### Use TestGen to Compare

1. Open TestGen React UI
2. Click "Compare Branches" tab
3. Fill in:
   - Repository URL: `file:///d:/AI/AI_test_gen/sample-crud-app`
   - Base Branch: `main`
   - Head Branch: `feature/product-search`
   - Language: `java`
   - Framework: `JUnit 5`
4. Click "Compare & Generate Tests"

**Expected Results:**
- TestGen detects changes in `ProductService.java` and `ProductController.java`
- Generates tests for the new `/api/products/search` endpoint
- Creates test cases for search functionality with keywords

---

## Scenario 2: Create Feature Branch - Add Order Status Tracking

### Create Feature Branch

```bash
git checkout main
git checkout -b feature/order-status-tracking
```

### Enhance OrderService

Edit `src/main/java/com/example/crud/service/OrderService.java`:

Add these methods:
```java
public List<Order> findByStatusAndUserId(String status, Long userId) {
    return orders.stream()
            .filter(o -> o.getStatus().equals(status) && o.getUserId().equals(userId))
            .toList();
}

public boolean updateOrderStatus(Long id, String newStatus) {
    return findById(id).map(order -> {
        order.setStatus(newStatus);
        return save(order) != null;
    }).orElse(false);
}
```

### Enhance OrderController

Edit `src/main/java/com/example/crud/controller/OrderController.java`:

Add these endpoints:
```java
@GetMapping("/status/{status}/user/{userId}")
public ResponseEntity<List<Order>> getOrdersByStatusAndUser(@PathVariable String status, @PathVariable Long userId) {
    return ResponseEntity.ok(orderService.findByStatusAndUserId(status, userId));
}

@PatchMapping("/{id}/status")
public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
    if (orderService.updateOrderStatus(id, status)) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    return ResponseEntity.notFound().build();
}
```

### Commit Changes

```bash
git add -A
git commit -m "Add order status tracking and update endpoint"
```

### Use TestGen to Compare

1. Open TestGen React UI
2. Click "Compare Branches" tab
3. Fill in:
   - Repository URL: `file:///d:/AI/AI_test_gen/sample-crud-app`
   - Base Branch: `main`
   - Head Branch: `feature/order-status-tracking`
   - Language: `java`
   - Framework: `JUnit 5`
4. Click "Compare & Generate Tests"

**Expected Results:**
- TestGen detects changes in `OrderService.java` and `OrderController.java`
- Generates tests for:
  - New `GET /api/orders/status/{status}/user/{userId}` endpoint
  - New `PATCH /api/orders/{id}/status` endpoint
- Creates test cases for status updates and filtered queries

---

## Scenario 3: Create Feature Branch - Add User Admin Functions

### Create Feature Branch

```bash
git checkout main
git checkout -b feature/admin-functions
```

### Enhance UserService

Edit `src/main/java/com/example/crud/service/UserService.java`:

Add these methods:
```java
public List<User> findByRole(String role) {
    return users.stream()
            .filter(u -> u.getRole() != null && u.getRole().equals(role))
            .toList();
}

public boolean changeUserRole(Long id, String newRole) {
    return findById(id).map(user -> {
        user.setRole(newRole);
        return save(user) != null;
    }).orElse(false);
}

public void deleteAllUsers() {
    users.clear();
}
```

### Enhance UserController

Edit `src/main/java/com/example/crud/controller/UserController.java`:

Add these endpoints:
```java
@GetMapping("/role/{role}")
public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
    return ResponseEntity.ok(userService.findByRole(role));
}

@PatchMapping("/{id}/role")
public ResponseEntity<User> changeUserRole(@PathVariable Long id, @RequestParam String newRole) {
    if (userService.changeUserRole(id, newRole)) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    return ResponseEntity.notFound().build();
}

@DeleteMapping("/admin/reset")
public ResponseEntity<Void> resetAllUsers() {
    userService.deleteAllUsers();
    return ResponseEntity.noContent().build();
}
```

### Commit Changes

```bash
git add -A
git commit -m "Add admin functions for user management"
```

### Use TestGen to Compare

1. Open TestGen React UI
2. Click "Compare Branches" tab
3. Fill in:
   - Repository URL: `file:///d:/AI/AI_test_gen/sample-crud-app`
   - Base Branch: `main`
   - Head Branch: `feature/admin-functions`
   - Language: `java`
   - Framework: `JUnit 5`
4. Click "Compare & Generate Tests"

**Expected Results:**
- TestGen detects changes in `UserService.java` and `UserController.java`
- Generates tests for:
  - New `GET /api/users/role/{role}` endpoint
  - New `PATCH /api/users/{id}/role` endpoint
  - New `DELETE /api/users/admin/reset` endpoint
- Creates comprehensive test cases for admin functions

---

## Quick Local Testing with Curl

### Test User API

```bash
# Get all users
curl http://localhost:8081/api/users

# Create user
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Bob Johnson", "email": "bob@example.com", "role": "USER"}'

# Get user by ID
curl http://localhost:8081/api/users/1

# Update user
curl -X PUT http://localhost:8081/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Bob Updated", "email": "bob.updated@example.com", "role": "ADMIN"}'

# Delete user
curl -X DELETE http://localhost:8081/api/users/1
```

### Test Product API

```bash
# Create product
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wireless Mouse",
    "description": "USB wireless mouse",
    "price": 29.99,
    "quantity": 50,
    "category": "Electronics"
  }'

# Get products by category
curl "http://localhost:8081/api/products/category/Electronics"

# Search products (after feature/product-search is merged)
curl "http://localhost:8081/api/products/search?keyword=Mouse"
```

### Test Order API

```bash
# Create order
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "status": "PENDING",
    "totalAmount": 99.99,
    "shippingAddress": "123 Main St, City, State 12345"
  }'

# Get orders by user
curl "http://localhost:8081/api/orders/user/1"

# Get orders by status
curl "http://localhost:8081/api/orders/status/PENDING"

# Update order status (after feature/order-status-tracking is merged)
curl -X PATCH "http://localhost:8081/api/orders/1/status?status=SHIPPED"
```

---

## Testing Workflow Summary

| Step | Action | Result |
|------|--------|--------|
| 1 | Create initial main branch | Working CRUD app |
| 2 | Create feature branch | New endpoint added |
| 3 | Use TestGen Compare Branches | Changed files detected |
| 4 | TestGen generates tests | Tests for new endpoints created |
| 5 | Review generated tests | Validate test coverage |
| 6 | Merge feature branch | Updated app with new features |

---

## Tips for Testing

1. **Single Feature Per Branch**: Each branch should contain one logical change
2. **Clear Commit Messages**: Use descriptive commit messages for clarity
3. **Use Local File URLs**: For local testing, use `file:///` URLs with TestGen
4. **Check Generated Tests**: Review TestGen output to verify it detected the changes
5. **Multiple Branches**: Create multiple feature branches to test different scenarios

---

## Troubleshooting

**Problem**: TestGen shows "No relevant files found"
- Solution: Ensure the branch has actual changes to Java files in `/controller/` or `/service/` directories

**Problem**: Generated tests seem incomplete
- Solution: Check that new endpoints follow standard Spring patterns (`@GetMapping`, `@PostMapping`, etc.)

**Problem**: "File not accessible" error
- Solution: Ensure Git is initialized and committed before running Compare Branches

---

## Next Steps

After testing with this app:
1. Use TestGen with your real application repository
2. Create multiple feature branches for parallel development
3. Use TestGen to validate test coverage for each branch
4. Integrate into CI/CD pipeline for automated test generation
