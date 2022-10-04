package com.starter.fullstack.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;

/**
 * Inventory Controller.
 * 
 * Modified by Michel T. on 09/29/22 Ready for review!
 * Updated on 10/03/22 by Michel T.
 */
@RestController
@RequestMapping("/inventory")
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
     * 
     * @return List of Product.
     */
    @GetMapping
    public List<Inventory> findInventories() {
        return this.inventoryDAO.findAll();
    }

    /**
     * Create Inventory
     * 
     * @param inventory
     * @return Inventory
     */
    @PostMapping
    public Inventory saveInventory(@Valid @RequestBody Inventory inventory) {
        return this.inventoryDAO.create(inventory);
    }
}

