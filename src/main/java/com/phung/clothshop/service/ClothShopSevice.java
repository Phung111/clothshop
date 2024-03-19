package com.phung.clothshop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.dto.order.BillDTO;
import com.phung.clothshop.domain.dto.order.BillResDTO;
import com.phung.clothshop.domain.dto.order.OrderItemDTO;
import com.phung.clothshop.domain.dto.order.OrderReqDTO;
import com.phung.clothshop.domain.dto.order.TotalResDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;
import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.order.Bill;
import com.phung.clothshop.domain.entity.order.CartItem;
import com.phung.clothshop.domain.entity.order.Order;
import com.phung.clothshop.domain.entity.order.OrderItem;
import com.phung.clothshop.domain.entity.order.Ship;
import com.phung.clothshop.domain.entity.order.Voucher;
import com.phung.clothshop.domain.entity.product.EProductStatus;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.repository.OrderRepository;
import com.phung.clothshop.service.address.IAddressService;
import com.phung.clothshop.service.bill.IBillService;
import com.phung.clothshop.service.cart.ICartService;
import com.phung.clothshop.service.cartItem.ICartItemService;
import com.phung.clothshop.service.customer.ICustomerService;
import com.phung.clothshop.service.order.IOrderService;
import com.phung.clothshop.service.orderitem.IOrderItemService;
import com.phung.clothshop.service.product.IProductService;
import com.phung.clothshop.service.ship.IShipService;
import com.phung.clothshop.service.voucher.IVoucherSevice;
import com.phung.clothshop.utils.ShipCal;

@Service
@Transactional
public class ClothShopSevice implements IClothShopService {

    @Autowired
    private ICartItemService iCartItemService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IShipService iShipService;

    @Autowired
    private IBillService iBillService;

    @Autowired
    private IAddressService iAddressService;

    @Autowired
    private IVoucherSevice iVoucherSevice;

    @Autowired
    private ShipCal shipCal;

    @Override
    public BillResDTO placeOrder(OrderReqDTO orderReqDTO, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "Session not foud");
        }

        Long customerID = (Long) session.getAttribute("CUSTOMER_ID");
        if (customerID == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "CUSTOMER_ID not found in session");
        }

        Order order = new Order();

        Optional<Customer> customerOptional = iCustomerService.findById(customerID);
        Customer customer = customerOptional.get();

        Optional<Address> addressOptional = iAddressService.findByCustomerIdAndIsDefaultTrue(customerID);
        Address address = addressOptional.get();
        AddressResDTO addressResDTO = address.toAddressResDTO();
        Long shipTotal = shipCal.calculate(address);
        Ship ship = new Ship();
        ship.setTotal(shipTotal);
        ship = iShipService.save(ship);

        Voucher voucher = new Voucher();
        VoucherResDTO voucherResDTO = new VoucherResDTO();

        if (orderReqDTO.getVoucherID() != null) {
            Optional<Voucher> voucherOptional = iVoucherSevice.findById(orderReqDTO.getVoucherID());
            voucher = voucherOptional.get();
            voucher.setDeleted(true);
            voucher = iVoucherSevice.save(voucher);
            voucher.checkDate();
            voucherResDTO = voucher.toVoucherResDTO();
        } else {
            voucher = null;
            voucherResDTO = null;
        }

        order.setCustomer(customer);
        order.setShip(ship);
        order.setVoucher(voucher);
        order = iOrderService.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        List<Long> cartItemIDsReq = orderReqDTO.getCartItemIDs();
        for (Long cartItemID : cartItemIDsReq) {
            Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemID);
            if (!cartItemOptional.isPresent()) {
                throw new CustomErrorException(HttpStatus.NOT_FOUND, "Cart Item not found with ID: " + cartItemID);
            }
            CartItem cartItem = cartItemOptional.get();
            OrderItem orderItem = cartItem.toOrderItem(order, cartItem.getProduct());
            orderItem = iOrderItemService.save(orderItem);
            iCartItemService.delete(cartItem);

            Product product = cartItem.getProduct();
            Long quantity = product.getQuantity() - cartItem.getQuantity();
            product.setQuantity(quantity);
            product.setSold(cartItem.getQuantity());
            if (quantity == 0) {
                product.setEProductStatus(EProductStatus.SOLDOUT);
            }
            iProductService.save(product);

            orderItems.add(orderItem);
            orderItemDTOs.add(orderItem.toOrderItemDTO());
        }

        order.setOrderItems(orderItems);
        order.calculateTotal();
        order = iOrderService.save(order);

        TotalResDTO totalResDTO = order.toTotalResDTO();

        Bill bill = order.toBill(order);
        Bill billSave = iBillService.save(bill);
        BillResDTO billResDTO = billSave.toBillResDTO(addressResDTO, orderItemDTOs, voucherResDTO, totalResDTO);

        return billResDTO;

    }

}
