import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito; // Library used to mock classes and methods
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class InventoryDAOTest {

    @Mock private MongoTemplate mockMongoTemplate;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this); // Initialize the mock
    }
     // Modified by Michel T. on 08/30/22. ****Ready For Review***

    @Test
    public void create() {
        // Follows unit testing AAA (Arrange/Act/Assert)
        // Arrange
        var inventoryDAO = new InventoryDAO(mockMongoTemplate); // Create your object injecting the mock
        var inventory = new Inventory();

        Mockito.when(mockMongoTemplate.insert(inventory)).thenReturn(inventory); // Set what your mock should do when it is executed with the parameter "inventory"

        // Act
        var actual = inventoryDAO.create(inventory);

        // Assert
        Assertions.assertNull(actual.getId());
        Mockito.verify(mockMongoTemplate, Mockito.times(1)).insert(inventory); // Verify the "insert" was executed once with 1 time with the parameter "inventory"
    }
}
