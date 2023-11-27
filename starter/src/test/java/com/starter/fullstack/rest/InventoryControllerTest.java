package com.starter.fullstack.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starter.fullstack.api.Inventory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InventoryControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private Inventory inventory;

  @Before
  public void setup() throws Throwable {
    this.inventory = new Inventory();
    this.inventory.setId("INVENTORY ID");
    this.inventory.setName("TEST INVENTORY");
    // Sets the Mongo ID for us
    this.inventory = this.mongoTemplate.save(this.inventory);
  }

  @After
  public void teardown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test findAll endpoint.
   * @throws Throwable see MockMvc
   *
   * */
  @Test
  public void findAll() throws Throwable {
    this.mockMvc.perform(get("/inventory")
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json("[" + this.objectMapper.writeValueAsString(inventory) + "]"));
  }

 /*
  * Test create endpoint.
  * @throws Throwable see MockMvc
 */
  @Test
  public void create() throws Throwable {
    this.inventory = new Inventory();
    this.inventory.setId("INVENTORY ID for CREATE");
    this.inventory.setName("TEST for CREATE");

    this.mockMvc.perform(post("/inventory")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(this.inventory)))
            .andExpect(status().isOk());

    Assert.assertEquals(2, this.mongoTemplate.findAll(Inventory.class).size());
  }

  /**
  * Test remove endpoint.
  * @throws Throwable see MockMvc
  */
  @Test
  public void remove() throws Throwable {
    this.mockMvc.perform(delete("/inventory/{id}", this.inventory.getId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    Assert.assertEquals(0, this.mongoTemplate.findAll(Inventory.class).size());
  }

  /**
   * Test update endpoint.
   * @throws Throwable see MockMvc
   */
  @Test
  public void update() throws Throwable {
    String ORIG_INVENTORY_ID = "INVENTORY ID FOR UPDATE TEST";
    String ORIG_INVENTORY_NAME = "OLD INVENTORY";
    String NEW_INVENTORY_NAME = "UPDATED INVENTORY";

    this.inventory = new Inventory();
    this.inventory.setId(ORIG_INVENTORY_ID);
    this.inventory.setName(ORIG_INVENTORY_NAME);
    this.inventory = this.mongoTemplate.save(this.inventory);

    this.inventory.setName(NEW_INVENTORY_NAME);
    this.mockMvc.perform(put("/inventory/{id}", this.inventory.getId(), this.inventory)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(this.inventory)))
      .andExpect(status().isOk());

    Assert.assertEquals(2, this.mongoTemplate.findAll(Inventory.class).size());
    Assert.assertEquals(this.inventory.getId(), ORIG_INVENTORY_ID);
    Assert.assertNotEquals(this.inventory.getName(), ORIG_INVENTORY_NAME);
  }

  /**
   * Test remove endpoint.
   * @throws Throwable see MockMvc
   */
  @Test
  public void retrieve() throws Throwable {
    this.inventory = new Inventory();
    this.inventory.setId("INVENTORY ID to FIND");
    this.inventory.setName("FOUND INVENTORY");
    this.inventory = this.mongoTemplate.save(this.inventory);

    this.mockMvc.perform(get("/inventory/{id}", this.inventory.getId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(this.inventory.getId()));
  }
}
