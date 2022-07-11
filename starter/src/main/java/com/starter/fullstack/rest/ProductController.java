package com.starter.fullstack.rest;

import com.starter.fullstack.api.Product;
import com.starter.fullstack.dao.ProductDAO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Product Controller.
 */
@RestController
public class ProductController {
  private final ProductDAO productDAO;

  /**
   * Default Constructor.
   * @param productDAO productDAO.
   */
  public ProductController(ProductDAO productDAO) {
    Assert.notNull(productDAO, "Product DAO must not be null.");
    this.productDAO = productDAO;
  }

  /**
   * Find Products.
   * @return List of Product.
   */
  @GetMapping("/products")
  public List<Product> findProducts() {
    return this.productDAO.findAll();
  }

  /**
   * Save Product.
   * @param product product.
   * @return Product.
   */
  @PostMapping("/products")
  public Product saveProduct(@Valid @RequestBody Product product) {
    return this.productDAO.save(product);
  }

  /**
   * Delete Product By Id.
   *
   * @param ids ids.
   */
  @DeleteMapping("/products")
  public void deleteProductById(@RequestBody List<String> ids) {
    Assert.notEmpty(ids, "Product Ids were not provided");
    this.productDAO.deleteProductsByIdIn(ids);
  }
}

