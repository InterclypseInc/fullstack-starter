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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    this.inventory.setId("ID");
    this.inventory.setName("TEST");
    // Sets the Mongo ID for us
    this.inventory = this.mongoTemplate.save(this.inventory);
  }

  @After
  public void teardown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test create endpoint
   * @throws Throwable see MockMvc
   */  
  @Test
  public void createTest() throws Throwable {
    int sizeBefore = this.mongoTemplate.findAll(Inventory.class).size();

    Inventory newInventory = new Inventory();
    newInventory.setId("NEW ID");
    newInventory.setName("CREATE TEST");
    newInventory.setProductType("test");
    
    this.mockMvc.perform(post("/inventory")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(newInventory)))
      .andExpect(status().isOk());

    Assert.assertEquals(sizeBefore + 1, this.mongoTemplate.findAll(Inventory.class).size());
  }

  /**
   * Test delete endpoint.
   * @throws Throwable see MockMvc
   */  
  @Test
  public void deleteTest() throws Throwable {
    int sizeBefore = this.mongoTemplate.findAll(Inventory.class).size();

    this.mockMvc.perform(delete("/inventory")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.inventory.getId()))
      .andExpect(status().isOk());

    Assert.assertEquals(sizeBefore - 1, this.mongoTemplate.findAll(Inventory.class).size());
  }
}
