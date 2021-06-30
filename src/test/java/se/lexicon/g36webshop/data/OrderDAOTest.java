package se.lexicon.g36webshop.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.g36webshop.ObjectSeeder;
import se.lexicon.g36webshop.model.entity.Customer;
import se.lexicon.g36webshop.model.entity.Order;
import se.lexicon.g36webshop.model.entity.OrderItem;
import se.lexicon.g36webshop.model.entity.Product;
import se.lexicon.g36webshop.model.misc.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderDAOTest {

    @Autowired
    private OrderDAO testObject;

    @Autowired
    private TestEntityManager em;

    private List<Order> ordersInContext;
    private List<Product> productsInContext;
    private List<Customer> customersInContext;

    @BeforeEach
    void setUp() {
        ObjectSeeder seeder = new ObjectSeeder();
        productsInContext = new ArrayList<>();
        for(Product product : seeder.products()){
            productsInContext.add(em.persist(product));
        }

        customersInContext = new ArrayList<>();
        for(Customer customer : seeder.customers()){
            customersInContext.add(em.persist(customer));
        }

        List<Order> orders = seeder.orders();
        orders.get(0).addOrderItem(new OrderItem(1, BigDecimal.ONE.multiply(productsInContext.get(0).getProductPrice()), productsInContext.get(0)));
        orders.get(0).addOrderItem(new OrderItem(1, BigDecimal.ONE.multiply(productsInContext.get(1).getProductPrice()), productsInContext.get(1)));
        orders.get(0).addOrderItem(new OrderItem(1, BigDecimal.ONE.multiply(productsInContext.get(2).getProductPrice()), productsInContext.get(2)));
        orders.get(1).addOrderItem(new OrderItem(100, BigDecimal.valueOf(100).multiply(productsInContext.get(0).getProductPrice()), productsInContext.get(0)));
        orders.get(0).setCustomer(customersInContext.get(0));
        orders.get(1).setCustomer(customersInContext.get(1));

        ordersInContext = testObject.saveAll(orders);

        em.flush();

    }

    @Test
    void context() {

    }

    @Test
    void findByCustomerCustomerId() {
        String customerId = customersInContext.get(0).getCustomerId();
        int expectedSize = 1;

        List<Order> result = testObject.findByCustomerCustomerId(customerId);
        assertEquals(expectedSize, result.size());

    }

    @Test
    void findByOrderContentProductProductNameIgnoreCase() {

    }

    @Test
    void findByProductName() {
        String productName = productsInContext.get(0).getProductName();
        int expectedSize = 2;

        List<Order> result = testObject.findByProductName(productName);
        assertEquals(expectedSize, result.size());
    }
}