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
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

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
    private Inventory i2;

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
        this.mockMvc.perform(post("/inventory")    // because ID doesn't matter, should be able to add same object twice
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.inventory)))
                .andExpect(status().isOk());

        Assert.assertEquals(3, this.mongoTemplate.findAll(Inventory.class).size());
        Assert.assertEquals(null, this.mongoTemplate.findById("OTHER ID", Inventory.class));  // make sure sets ID to null
    }

    // check to ensure that the item gets deleted
    @Test
    public void delete1() throws Throwable {
        this.i2 = new Inventory();
        i2.setId("OTHER_ID");
        i2.setName("OTHER_NAME");
        this.mongoTemplate.save(i2);

        this.mockMvc.perform(delete("/inventory")  // remove inventory
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.inventory.getId()))
                        .andExpect(status().isOk());
        Assert.assertEquals(1, this.mongoTemplate.findAll(Inventory.class).size()); // make sure i2 is still in there
        Assert.assertEquals(i2,this.mongoTemplate.findById(this.i2.getId(),Inventory.class));

        Assert.assertNull(this.mongoTemplate.findById(this.inventory.getId(),Inventory.class)); // make sure inventory got taken out

        MvcResult result = this.mockMvc.perform(delete("/inventory")  // try to remove inventory again (testing invalid id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.inventory.getId()))
                        .andExpect(status().isOk())
                        .andReturn();
        Assert.assertNull(result.getResponse().getContentType());
    }
}

