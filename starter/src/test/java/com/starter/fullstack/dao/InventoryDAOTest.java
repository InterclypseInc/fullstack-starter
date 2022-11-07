package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.config.EmbedMongoClientOverrideConfig;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
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
   * Test create method.
   */ 
  @Test
  public void create() {
    Inventory inventory = new Inventory();
    inventory.setId(null);
    inventory = this.inventoryDAO.create(inventory);
    
    Assert.assertNotNull(inventory.getId());
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }
  
   /**
   * Test delete method when passed ID is equal to just-created ID.
   */ 
  @Test
  public void validDelete() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("TEST");
    
    Inventory savedInventory = mongoTemplate.save(inventory);
    String targetId = savedInventory.getId();
    int preDelSize = mongoTemplate.findAll(Inventory.class).size();
    
    Optional<Inventory> deletedInventory = this.inventoryDAO.delete(targetId);
    int postDelSize = mongoTemplate.findAll(Inventory.class).size();
    
    Assert.assertNotNull(deletedInventory);
    Assert.assertEquals(preDelSize - 1, postDelSize);

  }
  
   /**
   * Test delete method when passed ID doesn't exist.
   */ 
  @Test
    public void invalidDelete() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId("TEST");
    
    Inventory savedInventory = mongoTemplate.save(inventory);
    String targetId = savedInventory.getId();
    int preDelSize = mongoTemplate.findAll(Inventory.class).size();
    
    Optional<Inventory> deletedInventory = this.inventoryDAO.delete("INVALIDID");
    int postDelSize = mongoTemplate.findAll(Inventory.class).size();
    
    Assert.assertTrue(deletedInventory.isEmpty());
    Assert.assertEquals(preDelSize, postDelSize);

  }
}
