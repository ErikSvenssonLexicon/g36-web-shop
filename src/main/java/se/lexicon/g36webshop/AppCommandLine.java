package se.lexicon.g36webshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.model.entity.*;
import se.lexicon.g36webshop.model.misc.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
@Slf4j
public class AppCommandLine implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setValue("Weapon");
        entityManager.persist(productCategory);

        Product laserRifle = new Product(
                null,
                "BFG1000",
                "Real working plasma rifle",
                BigDecimal.valueOf(4999999.99),
                new HashSet<>(Collections.singletonList(productCategory))
        );

        entityManager.persist(laserRifle);

        AppUser appUser = new AppUser();
        appUser.setUsername("terminator");
        appUser.setPassword("hastalavista");

        Address address = new Address(null, "Hjalmar Petris väg 32", "352 46", "Växjö", "Sweden");

        Customer customer = new Customer(
                null,
                "Arnold",
                "Schwarzenegger",
                "t-100@skynet.org",
                appUser,
                address
        );

        entityManager.persist(customer);

        OrderItem orderItem = new OrderItem(5, laserRifle.getProductPrice().multiply(BigDecimal.valueOf(5)), laserRifle);

        Order order = new Order();
        order.addOrderItem(orderItem);
        order.setCustomer(customer);
        order.setOrderStatus(OrderStatus.PENDING);

        entityManager.persist(order);

        log.info(order.toString());
        log.info(order.getCustomer().toString());
        log.info(order.getOrderContent().toString());

    }
}
