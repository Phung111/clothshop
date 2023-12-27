package com.phung.clothshop.api;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.model.dto.order.CartDTO;
import com.phung.clothshop.model.dto.order.CartItemDTO;
import com.phung.clothshop.model.order.Cart;
import com.phung.clothshop.model.order.CartItem;
import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.repository.CartItemRepository;
import com.phung.clothshop.service.cart.ICartService;
import com.phung.clothshop.service.cartItem.ICartItemService;
import com.phung.clothshop.service.product.IProductService;

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
    private CartItemRepository cartItemRepository;

    @GetMapping("/showCart")
    public ResponseEntity<?> showCart(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Long cartId = (Long) session.getAttribute("CART_ID");
        if (cartId == null) {
            return new ResponseEntity<>("CART_ID not found in session", HttpStatus.NOT_FOUND);
        }

        Optional<Cart> cartOptionalRes = iCartService.findById(cartId);
        Cart cartRes = cartOptionalRes.get();
        CartDTO cartDTO = cartRes.toCartDTO();

        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @PostMapping("/addItemToCart/{productId}")
    public ResponseEntity<?> addItemToCart(@PathVariable Long productId, HttpServletRequest request) {

        Optional<Product> productOptional = iProductService.findById(productId);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>("Product not found with ID: " + productId, HttpStatus.NOT_FOUND);
        }
        Product product = productOptional.get();

        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Long cartId = (Long) session.getAttribute("CART_ID");
        if (cartId == null) {
            return new ResponseEntity<>("CART_ID not found in session", HttpStatus.NOT_FOUND);
        }

        Optional<Cart> cartOptional = iCartService.findById(cartId);
        Cart cart = cartOptional.get();

        Boolean isCartItemExist = cartItemRepository.existsByProduct(product);
        if (isCartItemExist) {
            Optional<CartItem> cartItemOptional = iCartItemService.findByProductId(productId);
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setTotal(cartItem.getQuantity() * product.getPrice());
            iCartItemService.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity((long) 1);
            cartItem.setTotal(product.getPrice());
            cartItem.setVariation(product.toVariations());
            iCartItemService.save(cartItem);
        }

        Optional<Cart> cartOptionalRes = iCartService.findById(cartId);
        Cart cartRes = cartOptionalRes.get();
        CartDTO cartDTO = cartRes.toCartDTO();

        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @PostMapping("/increaseCartItem/{cartItemId}")
    public ResponseEntity<?> increaseCartItem(@PathVariable Long cartItemId, HttpServletRequest request) {
        Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            return new ResponseEntity<>("Cart Item has already deleted with ID: " + cartItemId, HttpStatus.NOT_FOUND);
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Long cartId = (Long) session.getAttribute("CART_ID");
        if (cartId == null) {
            return new ResponseEntity<>("CART_ID not found in session", HttpStatus.NOT_FOUND);
        }

        CartItem cartItem = cartItemOptional.get();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        CartItem cartItemRes = iCartItemService.save(cartItem);
        CartItemDTO cartItemResDTO = cartItemRes.toCartItemDTO();

        return new ResponseEntity<>(cartItemResDTO, HttpStatus.CREATED);
    }

    @PostMapping("/decreaseCartItem/{cartItemId}")
    public ResponseEntity<?> decreaseCartItem(@PathVariable Long cartItemId, HttpServletRequest request) {
        Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            return new ResponseEntity<>("Cart Item has already deleted with ID: " + cartItemId, HttpStatus.NOT_FOUND);
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Long cartId = (Long) session.getAttribute("CART_ID");
        if (cartId == null) {
            return new ResponseEntity<>("CART_ID not found in session", HttpStatus.NOT_FOUND);
        }

        CartItem cartItem = cartItemOptional.get();
        Long quantity = cartItem.getQuantity();
        cartItem.setQuantity(quantity - 1);
        if (quantity == 1) {
            iCartItemService.delete(cartItem);
            return new ResponseEntity<>("Delete cart item successfully", HttpStatus.CREATED);
        } else {
            CartItem cartItemRes = iCartItemService.save(cartItem);
            CartItemDTO cartItemResDTO = cartItemRes.toCartItemDTO();
            return new ResponseEntity<>(cartItemResDTO, HttpStatus.CREATED);
        }
    }

    @PostMapping("/deleteCartItem/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId, HttpServletRequest request) {
        Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            return new ResponseEntity<>("Cart Item has already deleted with ID: " + cartItemId, HttpStatus.NOT_FOUND);
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Long cartId = (Long) session.getAttribute("CART_ID");
        if (cartId == null) {
            return new ResponseEntity<>("CART_ID not found in session", HttpStatus.NOT_FOUND);
        }

        CartItem cartItem = cartItemOptional.get();
        iCartItemService.delete(cartItem);

        return new ResponseEntity<>("Cart Item deleted successfully with ID: " + cartItemId, HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam("cartItemIDsReq") String cartItemIDsReq,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Long cartId = (Long) session.getAttribute("CART_ID");
        if (cartId == null) {
            return new ResponseEntity<>("CART_ID not found in session", HttpStatus.NOT_FOUND);
        }

        String[] cartItemIDsString = cartItemIDsReq.split(",");

        List<Long> cartItemIDs = Arrays.stream(cartItemIDsString)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();

        for (Long cartItemID : cartItemIDs) {
            Optional<CartItem> cartItemOptional = iCartItemService.findById(cartItemID);
            CartItemDTO cartItemDTO = cartItemOptional.get().toCartItemDTO();
            cartItemDTOs.add(cartItemDTO);
        }

        return new ResponseEntity<>(cartItemDTOs, HttpStatus.OK);
    }

}
