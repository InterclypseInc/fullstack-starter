package com.starter.fullstack.dao;

import com.starter.fullstack.api.Product;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test Product DAO.
 */
@EnableMongoRepositories(basePackages = "com.starter.fullstack.dao")
@DataMongoTest
@RunWith(SpringRunner.class)
public class ProductDAOTest {
  @Resource
  private ProductDAO productDAO;
  private static final String PRODUCT_NAME = "name";

  @Before
  public void setup() {
    Product product = new Product();
    product.setName(PRODUCT_NAME);
    this.productDAO.save(product);
  }

  /**
   * Test Get Count Distinct method.
   */
  @Test
  public void findProductByName() {
    Optional<Product> optionalProduct = this.productDAO.findProductByName(PRODUCT_NAME);
    Assert.assertTrue(optionalProduct.isPresent());
    this.productDAO.delete(optionalProduct.get());
  }
}
