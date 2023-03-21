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

  // check to see if it deletes the  object if given a valid ID, and ensures that it returns null if deleting the same thing twice
  // also check to see if it deletes the right object if given a valid ID after 2 objects with same name/type were added
  @Test
  public void delete1() {
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
    Optional<Inventory> actualInventory2 = this.inventoryDAO.delete("TEST1"); //this implicitly checks to make sure it can handle an invalid ID as well (handles the "if i == null")
    Assert.assertEquals(null,actualInventory2);
  }

  // if id is null, check to make sure it returns null
  @Test
  public void delete2(){
    Optional<Inventory> actualInventory2 = this.inventoryDAO.delete(null);
    Assert.assertEquals(null,actualInventory2);
  }

  // Per PR comments, reduced create tests to only one test, which now ensures that the object is added the correct amount of times
  // (one in this instance), and that the ID is set to null
  @Test
  public void create1() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("temp");
    this.inventoryDAO.create(inventory);
    this.inventoryDAO.create(inventory); // it should be able to add the same object more than once as well since the ID doesn't matter
    List<Inventory> actualInventory = this.mongoTemplate.findAll(Inventory.class);
    Assert.assertEquals(2, actualInventory.size());
    Assert.assertEquals(null, this.mongoTemplate.findById("temp", Inventory.class));
  }

}
