package com.phung.clothshop.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.dto.cart.CartItemReqDTO;
import com.phung.clothshop.domain.dto.order.AddressShipResDTO;
import com.phung.clothshop.domain.dto.order.CartDTO;
import com.phung.clothshop.domain.dto.order.CartItemDTO;
import com.phung.clothshop.domain.dto.order.CheckoutResDTO;
import com.phung.clothshop.domain.dto.order.TotalResDTO;
import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.order.Cart;
import com.phung.clothshop.domain.entity.order.CartItem;
import com.phung.clothshop.domain.entity.order.Ship;
import com.phung.clothshop.domain.entity.order.Voucher;
import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.repository.CartItemRepository;
import com.phung.clothshop.service.JwtService;
import com.phung.clothshop.service.address.IAddressService;
import com.phung.clothshop.service.cart.ICartService;
import com.phung.clothshop.service.cartItem.ICartItemService;
import com.phung.clothshop.service.customer.ICustomerService;
import com.phung.clothshop.service.product.IProductService;
import com.phung.clothshop.service.voucher.IVoucherSevice;
import com.phung.clothshop.utils.AppUtils;
import com.phung.clothshop.utils.ShipCal;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/cart")
public class CartAPI {

    @Autowired
    private ICartService iCartService;

    @Autowired
    private ICartItemService iCartItemService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IAddressService iAddressService;

    @Autowired
    private IVoucherSevice iVoucherSevice;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShipCal shipCal;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> showCart(HttpServletRequest request) {
        try {
            String jwtToken = jwtService.extractJwtFromRequest(request);
            Long cartId = jwtService.getCartIdFromJwtToken(jwtToken);
            Optional<Cart> cartOptionalRes = iCartService.findById(cartId);
            Cart cartRes = cartOptionalRes.get();
            CartDTO cartDTO = cartRes.toCartDTO();

            return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PostMapping("/addCartItem")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> addCartItem(@Validated CartItemReqDTO cartItemReqDTO,
                                        HttpServletRequest request,
                                        BindingResult bindingResult) {

        Long productId = Long.parseLong(cartItemReqDTO.getIdProduct());                               

        Optional<Product> productOptional = iProductService.findById(productId);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>("Product not found with ID: " + productId, HttpStatus.NOT_FOUND);
        }
        Product product = productOptional.get();

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Long quantityCartItem = Long.parseLong(cartItemReqDTO.getQuantity());
        Long quantityProduct = product.getQuantity();

        if (quantityCartItem > quantityProduct) {
            throw new CustomErrorException(HttpStatus.CONFLICT, "The quantity exceeds the available product's stock.");
        }

        try {

            String jwtToken = jwtService.extractJwtFromRequest(request);
            Long cartId = jwtService.getCartIdFromJwtToken(jwtToken);
            Optional<Cart> cartOptional = iCartService.findById(cartId);
            Cart cart = cartOptional.get();

            Boolean isCartItemExist = cartItemRepository.existsByProduct(product);
            if (isCartItemExist) {
                Optional<CartItem> cartItemOptional = iCartItemService.findByProductId(productId);
                CartItem cartItem = cartItemOptional.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantityCartItem);
                cartItem.setTotal(cartItem.caculateTotal());
                iCartItemService.save(cartItem);
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantityCartItem);
                cartItem.setTotal(cartItem.caculateTotal());
                cartItem.setVariation(cartItemReqDTO.getColor() + ", " + cartItemReqDTO.getSize());
                iCartItemService.save(cartItem);
            }

            Optional<Cart> cartOptionalRes = iCartService.findById(cartId);
            Cart cartRes = cartOptionalRes.get();
            CartDTO cartDTO = cartRes.toCartDTO();

            return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @PostMapping("/increaseCartItem/{cartItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> increaseCartItem(@PathVariable Long cartItemId) {
        Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            return new ResponseEntity<>("Cart Item has already deleted with ID: " + cartItemId, HttpStatus.NOT_FOUND);
        }

        try {

            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setTotal(cartItem.caculateTotal());
            CartItem cartItemRes = iCartItemService.save(cartItem);
            CartItemDTO cartItemResDTO = cartItemRes.toCartItemDTO();

            return new ResponseEntity<>(cartItemResDTO, HttpStatus.CREATED);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @PostMapping("/decreaseCartItem/{cartItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> decreaseCartItem(@PathVariable Long cartItemId) {
        Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            return new ResponseEntity<>("Cart Item has already deleted with ID: " + cartItemId, HttpStatus.NOT_FOUND);
        }

        try {
            CartItem cartItem = cartItemOptional.get();
            Long quantity = cartItem.getQuantity();

            if (quantity == 1) {
                iCartItemService.delete(cartItem);
                return new ResponseEntity<>("Delete cart item successfully", HttpStatus.CREATED);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setTotal(cartItem.caculateTotal());
            CartItem cartItemRes = iCartItemService.save(cartItem);
            CartItemDTO cartItemResDTO = cartItemRes.toCartItemDTO();
                return new ResponseEntity<>(cartItemResDTO, HttpStatus.CREATED);
            }
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @DeleteMapping("/deleteCartItem/{cartItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId) {
        Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            return new ResponseEntity<>("Cart Item has already deleted with ID: " + cartItemId, HttpStatus.NOT_FOUND);
        }

        try {

            CartItem cartItem = cartItemOptional.get();
            iCartItemService.delete(cartItem);

            return new ResponseEntity<>(cartItemId, HttpStatus.OK);

        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @PostMapping("/changeQuantityCartItem/{cartItemId},{quantity}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> changeQuantityCartItem(@PathVariable Long cartItemId,@PathVariable Long quantity) {
        Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            return new ResponseEntity<>("Cart Item has already deleted with ID: " + cartItemId, HttpStatus.NOT_FOUND);
        }

        try {

            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);
            cartItem.setTotal(cartItem.caculateTotal());
            CartItem cartItemRes = iCartItemService.save(cartItem);
            CartItemDTO cartItemResDTO = cartItemRes.toCartItemDTO();

            return new ResponseEntity<>(cartItemResDTO, HttpStatus.CREATED);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @PostMapping("/checkout")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> checkout(@RequestBody List<Long> cartItemIDs,
            HttpServletRequest request) {

                if (cartItemIDs.size() == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have not selected any items for checkout");
                }

        try {
            String jwtToken = jwtService.extractJwtFromRequest(request);
            Long customerID = jwtService.getCustomerIdFromJwtToken(jwtToken);
 

            List<CartItemDTO> cartItemDTOs = new ArrayList<>();
            Long cartItemsTotal = 0L;

            for (Long cartItemID : cartItemIDs) {
                Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemID);
                if (!cartItemOptional.isPresent()) {
                    throw new CustomErrorException(HttpStatus.BAD_REQUEST,
                            String.format("Cant find cart item with id: %d", cartItemID));
                }
                CartItemDTO cartItemDTO = cartItemOptional.get().toCartItemDTO();
                cartItemDTOs.add(cartItemDTO);
                cartItemsTotal += cartItemDTO.getTotal();
            }

            TotalResDTO totalResDTO = new TotalResDTO();
            totalResDTO.setItemsTotal(cartItemsTotal);
            Optional<Address> addressOptional = iAddressService.findByCustomerIdAndIsDefaultTrue(customerID);
            Address address = addressOptional.get();
            Long shipTotal = shipCal.calculate(address);
            totalResDTO.setShipTotal(shipTotal);
            totalResDTO.caculateGrandTotal();

            CheckoutResDTO checkoutResDTO = new CheckoutResDTO();
            checkoutResDTO.setAddress(address.toAddressResDTO());
            checkoutResDTO.setCartItems(cartItemDTOs);
            checkoutResDTO.setTotal(totalResDTO);

            return new ResponseEntity<>(checkoutResDTO, HttpStatus.OK);

        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }


}
