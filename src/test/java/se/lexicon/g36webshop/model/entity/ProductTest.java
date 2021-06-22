package se.lexicon.g36webshop.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    public static final String PRODUCT_NAME = "product1";
    public static final String DESCRIPTION = "description1";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(10);

    public List<ProductCategory> productCategories(){
        return Arrays.asList(
                new ProductCategory(null, "category1", null),
                new ProductCategory(null, "category2", null),
                new ProductCategory(null, "category3", null),
                null
        );
    }

    private Product testObject;

    @BeforeEach
    void setUp() {
        testObject = new Product(null, PRODUCT_NAME, DESCRIPTION, PRODUCT_PRICE, new HashSet<>(productCategories()));
    }

    @Test
    @DisplayName("Test object is successfully instantiated by all arg constructor")
    void successfully_instantiated_testObject(){
        assertNotNull(testObject);
        assertNull(testObject.getProductId());
        assertEquals(PRODUCT_NAME, testObject.getProductName());
        assertEquals(DESCRIPTION, testObject.getDescription());
        assertEquals(PRODUCT_PRICE, testObject.getProductPrice());
        assertEquals(3, testObject.getCategories().size());
        assertTrue(testObject.getCategories().stream().allMatch(productCategory -> productCategory.getProductsWithCategory().contains(testObject)));
    }

    @Test
    @DisplayName("Called on empty new Product getCategories() return empty HashSet")
    void getCategories() {
        Product product = new Product();
        assertTrue(product.getCategories().isEmpty());
    }

    @Test
    @DisplayName("setCategories overwrite all previous relationships")
    void setCategories() {
        Set<ProductCategory> categorySet = new HashSet<>(Arrays.asList(
                new ProductCategory(null, "category4", null),
                new ProductCategory(null, "category5", null)
        ));
        Set<ProductCategory> oldCategories = testObject.getCategories();
        int expectedSize = 2;

        testObject.setCategories(categorySet);

        assertEquals(expectedSize, testObject.getCategories().size());
        assertTrue(testObject.getCategories().stream().allMatch(productCategory -> productCategory.getProductsWithCategory().contains(testObject)));
        assertTrue(oldCategories.stream().anyMatch(productCategory -> productCategory.getProductsWithCategory().contains(testObject)));
    }

    @Test
    @DisplayName("Given null setCategories clears all relationships")
    void setCategories_null() {
        Set<ProductCategory> oldCategories = testObject.getCategories();

        testObject.setCategories(null);

        assertNotNull(testObject.getCategories());
        assertTrue(testObject.getCategories().isEmpty());
        assertTrue(oldCategories.stream().noneMatch(productCategory -> productCategory.getProductsWithCategory().contains(testObject)));
    }

    @Test
    void addProductCategory() {
        ProductCategory productCategory = new ProductCategory(null, "test", null);
        testObject.addProductCategory(productCategory);

        assertEquals(4, testObject.getCategories().size());
        assertTrue(productCategory.getProductsWithCategory().contains(testObject));
    }

    @Test
    void addProductCategory_throws_IllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.addProductCategory(null)
        );
    }

    @Test
    void removeProductCategory() {
        ProductCategory productCategory = new ArrayList<>(testObject.getCategories()).get(0);
        testObject.removeProductCategory(productCategory);

        assertEquals(2, testObject.getCategories().size());
        assertFalse(productCategory.getProductsWithCategory().contains(testObject));
    }

    @Test
    void removeProductCategory_throws_IllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.removeProductCategory(null)
        );
    }
}