package com.starter.fullstack.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.api.Product;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private Inventory inventory2;

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
     * Test create endpoint.
     * @throws Throwable see MockMvc
     */
    @Test
    public void create1() throws Throwable {
        this.inventory = new Inventory();
        this.inventory.setId("OTHER ID");
        this.inventory.setName("ALSO TEST");
        this.mockMvc.perform(post("/inventory")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.inventory)))
                .andExpect(status().isOk());

        Assert.assertEquals(2, this.mongoTemplate.findAll(Inventory.class).size());
    }

    // check to make sure that it sets the ID to null
    @Test
    public void create2() throws Throwable {
        this.inventory = new Inventory();
        this.inventory.setId("OTHER ID");
        this.inventory.setName("ALSO TEST");
        this.mockMvc.perform(post("/inventory")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.inventory)))
                .andExpect(status().isOk());

        Assert.assertEquals(null, this.mongoTemplate.findById("OTHER ID", Inventory.class));
    }


    // check to make sure you can insert multiple times
    @Test
    public void create3() throws Throwable {
        this.inventory = new Inventory();
        this.inventory.setId("OTHER ID");
        this.inventory.setName("ALSO TEST");
        this.mockMvc.perform(post("/inventory")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.inventory)))
                .andExpect(status().isOk());
        this.inventory2 = new Inventory();
        this.inventory2.setId("OTHER OTHER ID");
        this.inventory2.setName("ALSO TEST");
        this.mockMvc.perform(post("/inventory")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.inventory2)))
                .andExpect(status().isOk());

        Assert.assertEquals(3, this.mongoTemplate.findAll(Inventory.class).size());
    }
}
