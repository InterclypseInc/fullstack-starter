# Task 1 - Inventory Create DAO

## Objective

Implement the Create operation for Inventory objects using Spring Data MongoDB and add unit testing for the new DAO method.

## Files Changed

* `starter/src/main/java/com/starter/fullstack/dao/InventoryDAO.java`
* `starter/src/test/java/com/starter/fullstack/dao/InventoryDAOTest.java`

## Changes Made

Implemented the `create()` method in `InventoryDAO.java`.

```java
public Inventory create(Inventory inventory) {
  inventory.setId(null);
  return this.mongoTemplate.insert(inventory);
}
```

The method clears any existing Inventory ID before insertion so MongoDB can generate a new ID for the object.

A new JUnit test was also added to verify that:

* The Inventory object is created successfully.
* MongoDB generates a new ID.
* An existing ID is not reused.
* The Inventory name and product type remain unchanged.

## Testing

Run the Inventory DAO tests with:

```bash
./gradlew test --tests com.starter.fullstack.dao.InventoryDAOTest
```

Run the full backend test suite with:

```bash
./gradlew test
```

## Result

The Inventory DAO now supports creating new Inventory objects in MongoDB and returning the created object with its generated ID.
