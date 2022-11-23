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
  private static final String TEST_ID = "TEST_ID";
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
   * createDaoTest checks if the createDao method is working as intended in InventoryDao file.
   */
  @Test
  public void createDaoTest() {
    Inventory inventory = new Inventory();
    inventory.setId(TEST_ID);
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Inventory actual = this.inventoryDAO.create(inventory);
    Assert.assertNotEquals(TEST_ID, actual.getId());
    Assert.assertFalse(actualInventory.isEmpty());
  }
  /**
   * deleteTest checks if the delete method is working as intedned in InventoryDao file.
   */
  @Test
 public void deleteTest() {
    Inventory inventory = new Inventory();
    inventory.setId(TEST_ID);
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    Optional<Inventory> deletedItem = this.inventoryDAO.delete(TEST_ID);

    Assert.assertTrue(deletedItem.isPresent());
    Assert.assertEquals(deletedItem.get().getName(), NAME);
    Assert.assertEquals(deletedItem.get().getProductType(), PRODUCT_TYPE);
  }
}
