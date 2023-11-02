package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


  /**
   * 
   * @param inv
   * @return inventory
   */
  @PostMapping
  public Inventory create(@Valid @RequestBody Inventory inv) {
    /* calling the InventoryDAO create method and adding the Inventory with ID, id, to
     * the database and returning the Inventory.
     */
    return this.inventoryDAO.create(inv);
  }

  /**
   * 
   * @param inv
   * @return inventory
   */
  @DeleteMapping public Inventory delete(@Valid @RequestBody String id) {

    /* calling the InventoryDAO delete method and removing the Inventory with ID, id, if
     * it exists in the database and returning that removed Inventory. If there is not an 
     * Inventory with ID, id, it will return null. 
     */
    return this.inventoryDAO.delete(id).orElse(null);
   
  }


}

