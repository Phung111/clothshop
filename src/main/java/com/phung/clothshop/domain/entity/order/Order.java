package com.phung.clothshop.domain.entity.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.order.OrderDTO;
import com.phung.clothshop.domain.dto.order.OrderItemDTO;
import com.phung.clothshop.domain.dto.order.TotalResDTO;
import com.phung.clothshop.domain.entity.customer.Customer;

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

    @OneToMany(mappedBy = "order", targetEntity = OrderItem.class, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "ship_id", referencedColumnName = "id", nullable = false)
    private Ship ship;

    @OneToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "id", nullable = true)
    private Voucher voucher;

    @Column
    private Long orderItemsTotal;
    @Column
    private Long shipTotal;
    @Column
    private Long voucherTotal;
    @Column
    private Long total;

    public void calculateTotal() {

        orderItemsTotal = 0L;

        for (OrderItem orderItem : orderItems) {
            orderItemsTotal += orderItem.getTotal();
        }

        shipTotal = ship.getTotal();
        if (shipTotal == null) {
            shipTotal = 0L;
        }

        Long voucherPercent;
        Long voucherPrice;

        if (voucher == null) {
            voucherPercent = 0L;
            voucherPrice = 0L;
        } else {
            if (voucher.getPercent() == null) {
                voucherPercent = 0L;
            } else {
                voucherPercent = voucher.getPercent();
            }

            if (voucher.getPrice() == null) {
                voucherPrice = 0L;
            } else {
                voucherPrice = voucher.getPrice();
            }
        }

        voucherTotal = orderItemsTotal * voucherPercent / 100 - voucherPrice;

        total = orderItemsTotal + shipTotal - voucherTotal;
    }

    public Bill toBill(Order order) {
        return new Bill()
                .setOrder(order)
                .setCustomer(customer)
                .setVoucher(voucher)
                .setOrderItemsTotal(orderItemsTotal)
                .setShipTotal(shipTotal)
                .setVoucherTotal(voucherTotal)
                .setTotal(total);

    }

    public TotalResDTO toTotalResDTO() {
        return new TotalResDTO()
                .setItemsTotal(orderItemsTotal)
                .setShipTotal(shipTotal)
                .setVoucherTotal(voucherTotal)
                .setGrandTotal(total);
    }

}
