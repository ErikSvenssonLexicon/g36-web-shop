package se.lexicon.g36webshop.model.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import se.lexicon.g36webshop.model.misc.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"orderId", "customer", "orderContent"})
@ToString(exclude = {"customer", "orderContent"})
@Entity
@Table(name = "orders")
@Slf4j
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false)
    private String orderId;
    private LocalDateTime lastUpdated;
    private BigDecimal priceTotal;
    private OrderStatus orderStatus;
    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "order",
            orphanRemoval = true
    )
    private List<OrderItem> orderContent;

    public Order(String orderId, LocalDateTime lastUpdated, BigDecimal priceTotal, OrderStatus orderStatus, Customer customer, List<OrderItem> orderContent) {
        this.orderId = orderId;
        this.lastUpdated = lastUpdated;
        this.priceTotal = priceTotal;
        this.orderStatus = orderStatus;
        this.customer = customer;
        setOrderContent(orderContent);
    }

    public List<OrderItem> getOrderContent() {
        if(orderContent == null) orderContent = new ArrayList<>();
        return orderContent;
    }

    public void setOrderContent(List<OrderItem> orderContent) {
        if(orderContent == null) orderContent = new ArrayList<>();
        while (orderContent.remove(null)) {
            log.warn("Removed null from list orderContent");
        }

        if(orderContent.isEmpty()){
            if(this.orderContent != null){
                for(OrderItem orderItem : this.orderContent){
                    orderItem.setOrder(null);
                }
            }
        }else {
            for(OrderItem orderItem : orderContent){
                orderItem.setOrder(this);
            }
        }
        this.orderContent = orderContent;
    }

    public void addOrderItem(OrderItem orderItem){
        if(orderItem == null) throw new IllegalArgumentException("OrderItem orderItem was null");
        if(orderContent == null) orderContent = new ArrayList<>();
        if(!orderContent.contains(orderItem)){
            orderContent.add(orderItem);
            orderItem.setOrder(this);
        }
    }

    public void removeOrderItem(OrderItem orderItem){
        if(orderItem == null) throw new IllegalArgumentException("OrderItem orderItem was null");
        if(orderContent == null) orderContent = new ArrayList<>();
        if(orderContent.remove(orderItem)){
            orderItem.setOrder(null);
        }
    }

    void calculatePriceTotal(){
        setPriceTotal(
                orderContent.stream()
                    .map(OrderItem::getItemPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    @PrePersist
    void prePersist(){
        lastUpdated = LocalDateTime.now();
        if(orderContent != null){
            calculatePriceTotal();
        }
    }

    @PreUpdate
    void preUpdate(){
        prePersist();
    }
}
