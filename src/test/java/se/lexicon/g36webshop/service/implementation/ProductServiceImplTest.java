package se.lexicon.g36webshop.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.TestObjectSeeder;
import se.lexicon.g36webshop.exception.AppResourceNotFoundException;
import se.lexicon.g36webshop.model.dto.product.ProductDTO;
import se.lexicon.g36webshop.model.entity.Product;
import se.lexicon.g36webshop.model.entity.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl testObject;

    @Autowired
    private TestEntityManager em;

    private List<Product> persistedProducts;
    private List<ProductCategory> persistedCategories;

    @BeforeEach
    void setUp() {
        TestObjectSeeder seeder = new TestObjectSeeder();
        persistedCategories = new ArrayList<>();
        for(ProductCategory category : seeder.categories()){
            persistedCategories.add(em.persist(category));
        }

        persistedProducts = seeder.products().stream()
                .map(em::persist)
                .collect(Collectors.toList());

        persistedProducts.get(0).addProductCategory(persistedCategories.get(0));
        persistedProducts.get(0).addProductCategory(persistedCategories.get(1));
        persistedProducts.get(1).addProductCategory(persistedCategories.get(0));
        persistedProducts.get(2).addProductCategory(persistedCategories.get(1));

        em.flush();
    }

    @Test
    @DisplayName("Given ProductDTO with valid values successfully persist new Product")
    void create() {
        ProductDTO productDTO = new ProductDTO();
        BigDecimal productPrice = BigDecimal.valueOf(90.99);
        String description = "\tSome description ";
        String productName = "Product name ";

        productDTO.setProductPrice(productPrice);
        productDTO.setDescription(description);
        productDTO.setProductName(productName);

        Product result = testObject.create(productDTO);

        assertNotNull(result);
        assertNotNull(result.getProductId());
        assertEquals(result.getProductName(), productName.trim());
        assertEquals(result.getDescription(), description.trim());
        assertEquals(result.getProductPrice(), productPrice);
    }

    @Test
    @DisplayName("Given null ProductDTO create() throws IllegalArgumentException")
    void create_throws_IllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.create(null)
        );
    }

    @Test
    @DisplayName("Given id findById successfully return object")
    void findById() {
        Product expected = persistedProducts.get(0);
        String id = expected.getProductId();

        Product result = testObject.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getProductId());
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Given invalidId findById throws AppResourceNotFoundException")
    void findById_throws_AppResourceNotFoundException() {
        String invalidId = "353453gge";
        assertThrows(
                AppResourceNotFoundException.class,
                () -> testObject.findById(invalidId)
        );
    }

    @Test
    @DisplayName("findAll return collection size 5")
    void findAll() {
        int expectedSize = 5;
        Collection<Product> result = testObject.findAll();

        assertEquals(expectedSize, result.size());
    }

    @Test
    @DisplayName("Given valid id and PersonDTO update success on non null fields")
    void update_success() {
        Product product = persistedProducts.get(1);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductPrice(BigDecimal.valueOf(100));

        Product result = testObject.update(product.getProductId(), productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getProductId(), result.getProductId());
        assertEquals(product.getProductName(), result.getProductName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(productDTO.getProductPrice(), result.getProductPrice());
    }

    @Test
    @DisplayName("Given null update throws IllegalArgumentException")
    void update_throws_IllegalArgumentException_on_null() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.update("foobar", null)
        );
    }

    @Test
    void update_throws_IllegalArgumentException_non_matching_ids() {
        Product product = persistedProducts.get(1);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(persistedProducts.get(0).getProductId());

        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.update(product.getProductId(), productDTO)
        );
    }

    @Test
    void delete() {
        em.flush();
        String id = persistedProducts.get(0).getProductId();

        testObject.delete(id);

        Product product = em.find(Product.class, id);
        assertNull(product);
    }

    @Test
    void findByProductCategoryValue() {
        String value = "category1";
        int expectedSize = 2;

        Collection<Product> result = testObject.findByProductCategoryValue(value);

        assertEquals(expectedSize, result.size());
    }

    @Test
    void findByProductNameContains() {
        String productName = "pro";
        int expectedSize = 5;

        Collection<Product> result = testObject.findByProductNameContains(productName);

        assertEquals(expectedSize, result.size());
    }

    @Test
    @DisplayName("Given productId and categoryId removeProductCategory return expected Product")
    void removeProductCategory_success() {
        String productId = persistedProducts.get(0).getProductId();
        String categoryId = persistedCategories.get(0).getCategoryId();

        Product result = testObject.removeProductCategory(productId, categoryId);
        em.flush();
        assertNotNull(result);
        assertEquals(1, result.getCategories().size());
        assertFalse(result.getCategories().stream().anyMatch(productCategory -> productCategory.getCategoryId().equals(categoryId)));
    }

    @Test
    @DisplayName("Given productId and categoryId addProductCategory return expected Product")
    void addProductCategory_success() {
        String productId = persistedProducts.get(1).getProductId();
        String categoryId = persistedCategories.get(1).getCategoryId();
        int expectedSize = 2;

        Product result = testObject.addProductCategory(productId, categoryId);
        em.flush();
        assertNotNull(result);
        assertEquals(expectedSize, result.getCategories().size());
        assertTrue(result.getCategories().stream().anyMatch(productCategory -> productCategory.getCategoryId().equals(categoryId)));
    }
}
