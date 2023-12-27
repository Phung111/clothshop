package com.phung.clothshop.model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.phung.clothshop.model.BaseEntity;
import com.phung.clothshop.model.customer.Customer;
import com.phung.clothshop.model.dto.order.OrderDTO;
import com.phung.clothshop.model.dto.order.OrderItemDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long total;

    @OneToMany(mappedBy = "order", targetEntity = OrderItem.class, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "ship_id", referencedColumnName = "id", nullable = false)
    private Ship ship;

    public OrderDTO toOrderDTO() {

        List<OrderItemDTO> orderitemDTOs = new ArrayList();
        for (OrderItem orderItem : orderItems) {
            orderitemDTOs.add(orderItem.toOrderItemDTO());
        }

        return new OrderDTO()
                .setOrderID(id)
                .setCustomerID(customer.getId())
                .setOrderitemDTOs(orderitemDTOs)
                .setShipTotal(ship.getTotal())
                .setTotal(total);

    }

    public void calculateTotal() {
        if (this.orderItems != null && !this.orderItems.isEmpty()) {
            long orderTotal = 0;
            for (OrderItem orderItem : orderItems) {
                orderTotal += orderItem.getTotal();
            }
            this.total = orderTotal + this.ship.getTotal();
        } else {
            this.total = this.ship.getTotal();
        }
    }

}
