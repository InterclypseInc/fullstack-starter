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
  private static final String ID = "101";
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
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId(ID);
    Inventory outInv = this.inventoryDAO.create(inventory);

    Assert.assertNotNull(outInv);
    Assert.assertNotEquals(outInv.getId(), ID);
    Assert.assertEquals(inventory.getName(), outInv.getName());
    Assert.assertEquals(inventory.getProductType(), outInv.getProductType());


    Inventory inventory2 = new Inventory();
    inventory2.setName("John");
    inventory2.setProductType(PRODUCT_TYPE);
    inventory2.setId(ID);
    Inventory outInv2 = this.inventoryDAO.create(inventory2);

    Assert.assertNotEquals(outInv.getId(), ID);
    Assert.assertNotEquals(outInv.getName(), outInv2.getName());
    Assert.assertEquals(this.mongoTemplate.estimatedCount(Inventory.class), 2);
  }

  /**
   * Test Delete method.
   */
  @Test
  public void delete() {
    // Deleting Inventory that doesn't exist
    Optional<Inventory> outInv = this.inventoryDAO.delete("23");
    Assert.assertFalse(outInv.isPresent());

    // Deleting Inventory that does exist
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventory.setId(ID);
    Inventory inv = this.inventoryDAO.create(inventory);
    Assert.assertEquals(this.mongoTemplate.estimatedCount(Inventory.class), 1);
    
    outInv = this.inventoryDAO.delete(inv.getId());
    Assert.assertTrue(outInv.isPresent());
    Assert.assertEquals(this.mongoTemplate.estimatedCount(Inventory.class), 0);
  }
}
