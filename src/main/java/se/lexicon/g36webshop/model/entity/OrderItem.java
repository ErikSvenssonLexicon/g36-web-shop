package se.lexicon.g36webshop.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"orderItemId", "order"})
@ToString(exclude = {"order"})
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false)
    private String orderItemId;
    private Integer amount;
    private BigDecimal itemPrice;
    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "product_id", table = "order_item")
    private Product product;

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_id", table = "order_item")
    private Order order;

    public OrderItem(String orderItemId, Integer amount, BigDecimal itemPrice, Product product, Order order) {
        this.orderItemId = orderItemId;
        this.amount = amount;
        this.itemPrice = itemPrice;
        this.product = product;
        this.order = order;
    }

    public OrderItem(Integer amount, BigDecimal itemPrice, Product product) {
        this(null, amount, itemPrice, product, null);
    }
}
