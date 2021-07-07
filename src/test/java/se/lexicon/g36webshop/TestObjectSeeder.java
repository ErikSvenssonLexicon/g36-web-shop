package se.lexicon.g36webshop;

import com.github.javafaker.Faker;
import se.lexicon.g36webshop.model.entity.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TestObjectSeeder {

    private Faker faker = Faker.instance();

    public List<Product> products(){
        return Arrays.asList(
                new Product(null, "Product1", "Description1", BigDecimal.valueOf(9.90), null),
                new Product(null, "Product2", "Description2", BigDecimal.valueOf(9.90), null),
                new Product(null, "Product3", "Description3", BigDecimal.valueOf(9.90), null),
                new Product(null, "Product4", "Description4", BigDecimal.valueOf(9.90), null),
                new Product(null, "Product5", "Description5", BigDecimal.valueOf(9.90), null)
        );
    }


    public List<ProductCategory> categories(){
        return Arrays.asList(
                new ProductCategory(null, "Category1", null),
                new ProductCategory(null, "Category2", null),
                new ProductCategory(null, "Category3", null)
        );
    }

    public List<Customer> customers(){
        return Arrays.asList(
                new Customer(null, faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), null, null),
                new Customer(null, faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), null, null),
                new Customer(null, faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), null, null),
                new Customer(null, faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), null, null),
                new Customer(null, faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), null, null)
        );
    }

    public List<AppUser> appUsers(){
        return Arrays.asList(
                new AppUser(null, faker.name().username(), faker.internet().password()),
                new AppUser(null, faker.name().username(), faker.internet().password()),
                new AppUser(null, faker.name().username(), faker.internet().password()),
                new AppUser(null, faker.name().username(), faker.internet().password()),
                new AppUser(null, faker.name().username(), faker.internet().password())
        );
    }

    public List<Address> addresses(){
        return Arrays.asList(
                new Address(null, faker.address().streetAddress()+" "+faker.address().buildingNumber(), "35002", "Växjö", "Sweden"),
                new Address(null, faker.address().streetAddress()+" "+faker.address().buildingNumber(), "35999", "Växjö", "Sweden"),
                new Address(null, faker.address().streetAddress()+" "+faker.address().buildingNumber(), "55001", "Jönköping", "Sweden"),
                new Address(null, faker.address().streetAddress()+" "+faker.address().buildingNumber(), "55002", "Jönköping", "Sweden")
        );
    }

}
