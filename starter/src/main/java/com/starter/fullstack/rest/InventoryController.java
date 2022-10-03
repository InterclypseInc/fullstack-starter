package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Inventory Controller.
 */
@RestController
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   *
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
  @PostMapping("/inventory")
  public Inventory create(@Valid @RequestBody Inventory inventory){
	return this.inventoryDAO.create(inventory);
  }
}

