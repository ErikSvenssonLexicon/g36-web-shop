package se.lexicon.g36webshop.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.TestObjectSeeder;
import se.lexicon.g36webshop.exception.AppResourceNotFoundException;
import se.lexicon.g36webshop.model.dto.product_category.ProductCategoryDTO;
import se.lexicon.g36webshop.model.entity.Product;
import se.lexicon.g36webshop.model.entity.ProductCategory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl testObject;

    @Autowired
    private TestEntityManager testEntityManager;

    List<ProductCategory> persistedCategories;
    List<Product> persistedProducts;

    @BeforeEach
    void setUp() {
        TestObjectSeeder seeder = new TestObjectSeeder();
        persistedProducts = seeder.products().stream()
                .map(testEntityManager::persist)
                .collect(Collectors.toList());

        persistedCategories = seeder.categories().stream()
                .map(testEntityManager::persist)
                .collect(Collectors.toList());


        persistedProducts.get(0).addProductCategory(persistedCategories.get(0));
        persistedProducts.get(0).addProductCategory(persistedCategories.get(1));
        persistedProducts.get(0).addProductCategory(persistedCategories.get(2));
        persistedProducts.get(1).addProductCategory(persistedCategories.get(2));
        persistedProducts.get(2).addProductCategory(persistedCategories.get(1));

        testEntityManager.flush();
    }

    @Test
    @DisplayName("Given ProductCategoryDTO create successfully persist to database")
    void create() {
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setValue("Category4");

        ProductCategory result = testObject.create(productCategoryDTO);

        assertNotNull(result);
        assertNotNull(result.getCategoryId());
        assertEquals(productCategoryDTO.getValue(), result.getValue());
        assertTrue(result.getProductsWithCategory().isEmpty());
    }

    @Test
    @DisplayName("Given null create throws IllegalArgumentException")
    void create_throws_IllegalArgumentException_on_null() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.create(null)
        );
    }

    @Test
    @DisplayName("Given valid id findById return expected")
    void findById() {
        ProductCategory expected = persistedCategories.get(0);
        String id = expected.getCategoryId();

        ProductCategory result = testObject.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getCategoryId());
        assertEquals(expected, result);
        assertEquals(1, result.getProductsWithCategory().size());
    }

    @Test
    @DisplayName("Given invalid id findById throws AppResourceNotFoundException")
    void findById_throws_AppResourceNotFoundException() {
        String invalidId = "fooBar";
        assertThrows(
                AppResourceNotFoundException.class,
                () -> testObject.findById(invalidId)
        );
    }

    @Test
    @DisplayName("findAll return collection of size 3")
    void findAll() {
        int expectedSize = 3;

        Collection<ProductCategory> result = testObject.findAll();

        assertEquals(expectedSize, result.size());

    }

    @Test
    @DisplayName("Given updated ProductCategoryDTO and valid id update success")
    void update() {
        String value = "\tCategory5 ";
        ProductCategory original = persistedCategories.get(0);
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setCategoryId(original.getCategoryId());

        productCategoryDTO.setValue(value);

        ProductCategory result = testObject.update(original.getCategoryId(), productCategoryDTO);

        assertNotNull(result);
        assertEquals(productCategoryDTO.getCategoryId(), result.getCategoryId());
        assertEquals(value.trim(), result.getValue());
        assertEquals(original.getProductsWithCategory().size(), result.getProductsWithCategory().size());
    }

    @Test
    void update_throws_IllegalArgumentException_on_null() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.update(null, null)
        );
    }

    @Test
    void update_throws_IllegalArgumentException_on_non_matching_Ids() {
        ProductCategory original = persistedCategories.get(0);
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setCategoryId("Foo");

        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.update(original.getCategoryId(), productCategoryDTO)
        );
    }

    @Test
    void delete() {
        String id = persistedCategories.get(0).getCategoryId();

        testObject.delete(id);

        assertNull(testEntityManager.find(ProductCategory.class, id));
    }

    @Test
    void findByFirstLetter() {
        String firstLetter = "c";
        Pageable pageable = Pageable.ofSize(10);

        int expectedSize = 3;

        Collection<ProductCategory> result = testObject.findByFirstLetter(firstLetter, pageable);
        assertEquals(expectedSize, result.size());
        assertTrue(result.stream().allMatch(productCategory -> productCategory.getValue().toLowerCase().startsWith(firstLetter)));
    }

    @Test
    void findByValueContains() {
        String value = "cat";
        int expectedSize = 3;
        Collection<ProductCategory> result = testObject.findByValueContains(value);

        assertEquals(expectedSize, result.size());
    }
}