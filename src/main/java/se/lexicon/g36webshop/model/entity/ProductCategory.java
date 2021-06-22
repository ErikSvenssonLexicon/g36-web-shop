package se.lexicon.g36webshop.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"categoryId", "productsWithCategory"})
@ToString(exclude = "productsWithCategory")
@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false)
    private String categoryId;
    @Column(unique = true)
    private String value;

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY,
            mappedBy = "categories"
    )
    private Set<Product> productsWithCategory;

    public Set<Product> getProductsWithCategory() {
        if(productsWithCategory == null) productsWithCategory = new HashSet<>();
        return productsWithCategory;
    }
}
