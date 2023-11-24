package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Test Inventory DAO.
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @ClassRule
  public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String PRODUCT_TYPE = "hops";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test Find All method.
   */
  @Test
  public void findAll() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }

  /**
   * Test Create method.
   */
  @Test
  public void create() {
    String INVENTORY_NAME = "Best Inventory Ever";

    // Create and add inventory to db
    Inventory newInventory = new Inventory();
    newInventory.setName(INVENTORY_NAME);
    newInventory.setProductType(PRODUCT_TYPE);
    String origId = newInventory.getId();

    // Check if successfully added to db
    Inventory addedInventory = this.inventoryDAO.create(newInventory);
    Assert.assertNotNull(addedInventory);

    // Checks if mongo ID overrides original ID
    Assert.assertNotEquals(addedInventory.getId(), origId);
  }

  /**
   * Test Create method.
   */
  @Test
  public void remove() {
    String INVENTORY_NAME = "Worst Inventory Ever";

    // Create and add inventory to db
    Inventory newInventory = new Inventory();
    newInventory.setName(INVENTORY_NAME);
    newInventory.setProductType(PRODUCT_TYPE);
    newInventory = this.inventoryDAO.create(newInventory);
    Assert.assertEquals(1, this.mongoTemplate.findAll(Inventory.class).size());

    // Check if successfully removed from db
    Optional<Inventory> deletedInventory = this.inventoryDAO.delete(newInventory.getId());
    Assert.assertNotNull(deletedInventory);
    Assert.assertEquals(0, this.mongoTemplate.findAll(Inventory.class).size());
  }
}