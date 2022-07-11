package com.starter.fullstack.dao;

import com.starter.fullstack.api.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Product DAO
 */
public interface ProductDAO extends MongoRepository<Product, String> {

  /**
   * Find Products By Their Name.
   * @param name The Product Name to Query by.
   * @return Found Product.
   */
  Optional<Product> findProductByName(String name);

  /**
   * Remove all products by ids.
   * @param ids ids.
   */
  void deleteProductsByIdIn(List<String> ids);
}
