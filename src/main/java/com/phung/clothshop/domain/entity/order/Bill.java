package com.phung.clothshop.domain.entity.order;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.dto.order.BillDTO;
import com.phung.clothshop.domain.dto.order.BillResDTO;
import com.phung.clothshop.domain.dto.order.OrderDTO;
import com.phung.clothshop.domain.dto.order.OrderItemDTO;
import com.phung.clothshop.domain.dto.order.TotalResDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.order.Order;

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

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

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

    public BillResDTO toBillResDTO(AddressResDTO addressResDTO, List<OrderItemDTO> orderItemDTOs,
            VoucherResDTO voucherResDTO, TotalResDTO totalResDTO) {
        return new BillResDTO()
                .setBillID(id)
                .setCustomerID(customer.getId())
                .setOrderID(order.getId())
                .setAddressResDTO(addressResDTO)
                .setOrderItemDTOs(orderItemDTOs)
                .setVoucherResDTO(voucherResDTO)
                .setTotalResDTO(totalResDTO);

    }

}
