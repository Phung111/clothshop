package com.phung.clothshop.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.phung.clothshop.model.customer.Customer;
import com.phung.clothshop.model.dto.order.CartItemDTO;
import com.phung.clothshop.model.order.Bill;
import com.phung.clothshop.model.order.CartItem;
import com.phung.clothshop.model.order.Order;
import com.phung.clothshop.model.order.OrderItem;
import com.phung.clothshop.model.order.Ship;
import com.phung.clothshop.model.product.EProductStatus;
import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.service.bill.IBillService;
import com.phung.clothshop.service.cart.ICartService;
import com.phung.clothshop.service.cartItem.ICartItemService;
import com.phung.clothshop.service.customer.ICustomerService;
import com.phung.clothshop.service.order.IOrderService;
import com.phung.clothshop.service.orderitem.IOrderItemService;
import com.phung.clothshop.service.product.IProductService;
import com.phung.clothshop.service.ship.IShipService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/order")
public class OrderAPI {

    @Autowired
    private ICartService iCartService;

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

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestParam("cartItemIDsReq") String cartItemIDsReq,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }

        Long customerID = (Long) session.getAttribute("CUSTOMER_ID");
        if (customerID == null) {
            return new ResponseEntity<>("CUSTOMER_ID not found in session", HttpStatus.NOT_FOUND);
        }

        Optional<Customer> customerOptional = iCustomerService.findById(customerID);
        Customer customer = customerOptional.get();
        Ship ship = iShipService.save(new Ship());
        Order orderSave = new Order();
        orderSave.setCustomer(customer);
        orderSave.setShip(ship);
        iOrderService.save(orderSave);

        String[] cartItemIDsString = cartItemIDsReq.split(",");
        List<Long> cartItemIDs = Arrays.stream(cartItemIDsString)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        for (Long cartItemID : cartItemIDs) {
            Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemID);
            if (!cartItemOptional.isPresent()) {
                return new ResponseEntity<>("Cart Item not found with ID: " + cartItemID, HttpStatus.NOT_FOUND);
            }
            CartItem cartItem = cartItemOptional.get();
            OrderItem orderItem = cartItem.toOrderItem(orderSave, cartItem.getProduct());
            iOrderItemService.save(orderItem);
            iCartItemService.delete(cartItem);

            Product product = cartItem.getProduct();
            Long quantity = product.getQuantity() - cartItem.getQuantity();
            product.setQuantity(quantity);
            if (quantity == 0) {
                product.setEProductStatus(EProductStatus.SOLDOUT);
            }
            iProductService.save(product);

        }

        Optional<Order> orderOptional = iOrderService.findById(orderSave.getId());
        Order orderFind = orderOptional.get();
        orderFind.calculateTotal();
        iOrderService.save(orderFind);

        Bill billSave = new Bill();
        billSave.setOrder(orderFind);
        billSave.setCustomer(customer);

        return new ResponseEntity<>(orderFind.toOrderDTO(), HttpStatus.OK);
    }

}
