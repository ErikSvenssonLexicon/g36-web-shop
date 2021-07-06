package se.lexicon.g36webshop.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "categories")
@EqualsAndHashCode(exclude = {"productId", "categories"})
@Entity
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false)
    private String productId;
    private String productName;
    private String description;
    private BigDecimal productPrice;

    public Product(String productId, String productName, String description, BigDecimal productPrice, Set<ProductCategory> categories) {
        setProductId(productId);
        setProductName(productName);
        setDescription(description);
        setProductPrice(productPrice);
        setCategories(categories);
    }

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "product_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_category_id")
    )
    private Set<ProductCategory> categories;

    public Set<ProductCategory> getCategories() {
        if(categories == null) categories = new HashSet<>();
        return categories;
    }

    public void setCategories(Set<ProductCategory> categories) {
        if(categories == null) categories = new HashSet<>();
        categories.remove(null); //Since a HashSet can contain ONE null i am removing null
        if(categories.isEmpty()){
            if(this.categories != null){
                for(ProductCategory category : this.categories){
                    if(category != null){
                        category.getProductsWithCategory().remove(this);
                    }
                }
            }
        }else {
            for(ProductCategory category : categories){
                if(category != null){
                    category.getProductsWithCategory().add(this);
                }
            }
        }
        this.categories = categories;
    }

    public void addProductCategory(ProductCategory productCategory){
        if(productCategory == null) throw new IllegalArgumentException("ProductCategory was null");
        if(categories == null) categories = new HashSet<>();
        if(categories.add(productCategory)){
            productCategory.getProductsWithCategory().add(this);
        }
    }

    public void removeProductCategory(ProductCategory productCategory){
        if(productCategory == null) throw new IllegalArgumentException("ProductCategory was null");
        if(categories == null) categories = new HashSet<>();
        if(categories.remove(productCategory)){
            productCategory.getProductsWithCategory().remove(this);
        }
    }
}
