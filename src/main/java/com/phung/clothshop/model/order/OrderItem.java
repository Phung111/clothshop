package com.phung.clothshop.model.order;

import java.util.Date;

import javax.persistence.*;

import com.phung.clothshop.model.BaseEntity;
import com.phung.clothshop.model.dto.order.OrderItemDTO;
import com.phung.clothshop.model.product.Product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column
    private String variation;

    @Column
    private Long quantity;

    @Column
    private Long total;

    public OrderItemDTO toOrderItemDTO() {
        return new OrderItemDTO()
                .setOrderItemID(id)
                .setOrderID(order.getId())
                .setProductID(product.getId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setVariation(variation)
                .setQuantity(quantity)
                .setTotal(quantity * product.getPrice());
    }

}
