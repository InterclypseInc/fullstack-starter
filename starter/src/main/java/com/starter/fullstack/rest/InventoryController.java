package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * Inventory Controller.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   * @param inventoryDAO inventoryDAO.
   */
  public InventoryController(InventoryDAO inventoryDAO) {
    Assert.notNull(inventoryDAO, "Inventory DAO must not be null.");
    this.inventoryDAO = inventoryDAO;
  }

  /**
   * Find Products.
   * @return List of Product.
   */
  @GetMapping
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }

  @PostMapping
  public Inventory create(Inventory inventory){
    return this.inventoryDAO.create(inventory);
  }

  @DeleteMapping
  public Optional<Inventory> delete(@RequestBody String id){
    return this.inventoryDAO.delete(id);
  }
}

