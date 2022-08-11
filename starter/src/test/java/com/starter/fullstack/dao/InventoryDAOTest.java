package com.starter.fullstack.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.config.EmbedMongoClientOverrideConfig;
import java.util.List;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test Inventory DAO.
 */
@ContextConfiguration(classes = {EmbedMongoClientOverrideConfig.class})
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String PRODUCT_TYPE = "hops";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
    //creates a new collection
    this.mongoTemplate.createCollection(Inventory.class);
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
   * Tests the creation of an Inventory object.
   * */
  @Test
  public void testCreateInventoryObject() {
    Inventory obj1 = new Inventory();
    obj1.setName("obj1");
    inventoryDAO.create(obj1);
    //Checks if collection exist
    Assert.assertTrue(this.mongoTemplate.collectionExists(Inventory.class));
    //Check if the object is in the collection
    Assert.assertEquals(this.mongoTemplate.findOne(new Query(where("name").is("obj1")), Inventory.class), obj1);
  }

  /**
   * Tests the deletion of an Inventory object.
   */
  @Test
  public void testDeleteInventoryObject() {
    Assert.assertTrue(this.mongoTemplate.collectionExists(Inventory.class));
    Inventory obj1 = new Inventory();
    obj1.setName("obj1");
    Assert.assertEquals(inventoryDAO.create(obj1), obj1);	
    inventoryDAO.delete(obj1.getId());
    Assert.assertEquals(this.mongoTemplate.findOne(new Query(where("name").is("obj1")), Inventory.class), null);
  }
}