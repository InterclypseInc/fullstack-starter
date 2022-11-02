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
    List<Inventory> inventoryList = this.mongoTemplate.findAll(Inventory.class);
    int before = inventoryList.size();
    
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    Inventory savedInventory = this.inventoryDAO.create(inventory);
    
    inventoryList = this.mongoTemplate.findAll(Inventory.class);
    
    Assert.assertEquals(inventory.getName(), savedInventory.getName());
    Assert.assertEquals(inventory.getProductType(), savedInventory.getProductType());
    Assert.assertEquals(inventoryList.size(), before + 1);
  }

  /**
   * Test delete method.
   */
  @Test
  public void delete() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
      
    List<Inventory> inventoryList = this.mongoTemplate.findAll(Inventory.class);
    int before = inventoryList.size();
    
    Optional<Inventory> deletedInventory = this.inventoryDAO.delete(inventory.getId());
    
    inventoryList = this.mongoTemplate.findAll(Inventory.class);

    Assert.assertEquals(inventory, deletedInventory.get());
    Assert.assertEquals(inventoryList.size(), before - 1);    
  }
}
