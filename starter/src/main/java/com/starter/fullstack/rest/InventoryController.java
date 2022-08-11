package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import java.util.Optional;
//import javax.validation.Vaild;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inventory Controller.
 */
@RestController
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
  @GetMapping("/inventory")
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }

  /**
   * Create Inventory item
   * @param inventory inventory
   * @return Inventory
   */
  @PostMapping("/inventory")
  public Inventory createInventory(@RequestBody Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }
  
  /**
   * Delete Inventory item
   * @param String id
   * @return Inventory
   */
  @DeleteMapping("/inventory")
  public Inventory deleteInventory(@RequestBody String id) {
    Optional<Inventory> foo = this.inventoryDAO.delete(id);
    return foo.get();
  }
}