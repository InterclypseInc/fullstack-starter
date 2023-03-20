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

  // check to see if it deletes the  object if given a valid ID
  @Test
  public void delete1() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("TEST1");
    this.mongoTemplate.save(inventory);
    Optional<Inventory> actualInventory = this.inventoryDAO.delete("TEST1");
    Assert.assertEquals(actualInventory, Optional.of(inventory));
  }

  // check to make sure it doesn't break if given an invalid ID
  @Test
  public void delete2() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("TEST1");
    this.mongoTemplate.save(inventory);
    Optional<Inventory> actualInventory = this.inventoryDAO.delete("INVALID ID");
    Assert.assertEquals(null,actualInventory);
  }

  // checking to ensure it does not break if I try to delete the same thing twice
  @Test
  public void delete3() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("TEST1");
    this.mongoTemplate.save(inventory);
    Optional<Inventory> actualInventory = this.inventoryDAO.delete("TEST1");
    Assert.assertEquals(actualInventory, Optional.of(inventory));
    Optional<Inventory> actualInventory2 = this.inventoryDAO.delete("TEST1");
    Assert.assertEquals(null,actualInventory2);
  }

  // check to see if it deletes the right object if given a valid ID after 2 objects with same name/type were added
  @Test
  public void delete4() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("TEST1");
    this.mongoTemplate.save(inventory);

    Inventory inventory2 = new Inventory();
    inventory2.setName(NAME);
    inventory2.setProductType(PRODUCT_TYPE);
    inventory2.setId("TEST2");
    this.mongoTemplate.save(inventory2);
    Optional<Inventory> actualInventory = this.inventoryDAO.delete("TEST1");
    Assert.assertEquals(actualInventory, Optional.of(inventory));
  }

  // see if you can insert just one object
  @Test
  public void create1() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory);
    List<Inventory> actualInventory = this.mongoTemplate.findAll(Inventory.class);
    Assert.assertFalse(actualInventory.isEmpty());
  }

  // make sure that it can insert more than one object
  @Test
  public void create2() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory);
    Inventory inventory2 = new Inventory();
    inventory2.setName("banana");
    inventory.setProductType("fruit");
    this.inventoryDAO.create(inventory2);
    List<Inventory> actualInventory = this.mongoTemplate.findAll(Inventory.class);
    Boolean check = actualInventory.size()==2;
    Assert.assertTrue(check);
  }

  // check to make sure it sets the ID to null
  @Test
  public void create3() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("temp");
    this.inventoryDAO.create(inventory);
    Inventory actualInventory = this.mongoTemplate.findById("temp", Inventory.class);
    Boolean check = actualInventory == null;
    Assert.assertTrue(check);
  }

  // check to make sure it can insert an object with the same name and product type multiple[le times
  @Test
  public void create4() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory);
    Inventory inventory2 = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory2);
    List<Inventory> actualInventory = this.mongoTemplate.findAll(Inventory.class);
    Boolean check = actualInventory.size()==2;
    Assert.assertTrue(check);
  }

}
