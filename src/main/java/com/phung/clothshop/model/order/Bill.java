package com.phung.clothshop.model.order;

import com.phung.clothshop.model.BaseEntity;
import com.phung.clothshop.model.customer.Customer;
import com.phung.clothshop.model.order.Order;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bills")
public class Bill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long total;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    public void calculateTotal() {
        if (order != null) {
            total = order.getTotal();
        } else {
            total = 0L;
        }
    }

}
