package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import java.util.Optional;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
  public List<Inventory> findInventories() { return this.inventoryDAO.findAll(); }

  /**
   * Save Inventory.
   * @param inventory inventory.
   * @return Saved Inventory.
   */
  @PostMapping
  public Inventory createInventory(Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }

  /**
   * Delete Inventory.
   * @param id ID of Inventory.
   * @return Deleted Inventory.
   */
  @DeleteMapping("/{id}")
  public Optional <Inventory> deleteInventoryById(@PathVariable String id) {
    Assert.notNull(id, "Inventory Id was not provided");
    return this.inventoryDAO.delete(id);
  }
}
