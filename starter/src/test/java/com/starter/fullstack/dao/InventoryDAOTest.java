package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.api.Product;

import java.util.List;
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
   * Testing the InventoryDAO Create method. First check to ensure only one inventory
   * item is currently stored in the data base. Then, check to see if that one that 
   * was added still has a null Mongo ID, or if it has been succesfully assigned.
   */
  @Test
  public void createTest() {

    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory);

    Assert.assertEquals(1, this.mongoTemplate.findAll(Inventory.class).size());

    Assert.assertNotEquals(null, this.mongoTemplate.findAll(Inventory.class).get(0).getId()); 
  }

  @Test
  public void deleteTest() { 

    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory);


    /* Assuring that the inventory was sucessfully added to the Mongo Template */
    Assert.assertEquals(1, this.mongoTemplate.findAll(Inventory.class).size());

    /* Removing the previosuly added inventory from the MongoTemplate */
    this.inventoryDAO.delete(inventory.getId());

     /* Retrieving a list of all the inventory objects in the MongoTemplate (database) */
     List<Inventory> actualInventory = this.inventoryDAO.findAll();

     /* checking to ensure that the list is empty because calling delete() should remove the single element from the MongoTemplate(database) */
     Assert.assertTrue(actualInventory.isEmpty());
    
  }

}
