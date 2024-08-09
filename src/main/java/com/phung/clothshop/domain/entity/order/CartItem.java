package com.phung.clothshop.domain.entity.order;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.order.CartItemDTO;
import com.phung.clothshop.domain.entity.product.Product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_items")
@Where(clause = "deleted = false")
public class CartItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column
    private String variation;

    @Column
    private Long quantity;

    @Column
    private Long total;

    public CartItemDTO toCartItemDTO() {
        return new CartItemDTO()
                .setCartItemId(id)
                .setCartId(cart.getId())
                .setProductId(product.getId())
                .setCategory(product.getCategory())
                .setImage(product.getImages().get(0).toProductImageDTO())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setPriceTotal(product.getPriceTotal())
                .setDiscount(product.getDiscount() != null ? product.getDiscount().toDiscountResDTO() : null)
                .setVariation(variation)
                .setQuantity(quantity)
                .setTotal(total);
    }

    public OrderItem toOrderItem(Order order, Product product) {
        return new OrderItem()
                .setOrder(order)
                .setProduct(product)
                .setVariation(variation)
                .setQuantity(quantity)
                .setTotal(total);

    }

    public Long caculateTotal() {
        long totalCal = quantity * product.getPrice();
        if (product.getDiscount() != null) {
            totalCal = totalCal/100 * (100-product.getDiscount().getPercent());
        }
        return totalCal;
    }

}
