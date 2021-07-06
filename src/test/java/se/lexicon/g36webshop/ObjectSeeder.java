package se.lexicon.g36webshop;

import com.github.javafaker.Faker;
import se.lexicon.g36webshop.model.entity.Customer;
import se.lexicon.g36webshop.model.entity.Order;
import se.lexicon.g36webshop.model.entity.Product;
import se.lexicon.g36webshop.model.misc.OrderStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ObjectSeeder {

    private Faker faker = Faker.instance();

    public List<Product> products(){
        return Arrays.asList(
                new Product(null, faker.lorem().characters(2, 20), faker.lorem().characters(2, 20), BigDecimal.valueOf(10), null),
                new Product(null, "Product2", "Decription2", BigDecimal.valueOf(10), null),
                new Product(null, "Product3", "Decription3", BigDecimal.valueOf(10), null),
                new Product(null, "Product4", "Decription4", BigDecimal.valueOf(10), null),
                new Product(null, "Product5", "Decription5", BigDecimal.valueOf(10), null)
        );
    }

    public List<Customer> customers(){
        return Arrays.asList(
                new Customer(null, "Test1", "Test1", "test1@gmail.com", null, null),
                new Customer(null, "Test2", "Test2", "test2@gmail.com", null, null)
        );
    }

    public List<Order> orders(){
        return Arrays.asList(
                new Order(null, null, null, OrderStatus.PENDING, null, null),
                new Order(null, null, null, OrderStatus.PENDING, null, null)
        );
    }

}
