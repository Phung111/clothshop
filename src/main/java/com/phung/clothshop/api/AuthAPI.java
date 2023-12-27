package com.phung.clothshop.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.model.account.Account;
import com.phung.clothshop.model.account.ERole;
import com.phung.clothshop.model.customer.Customer;
import com.phung.clothshop.model.dto.account.AccountLoginReqDTO;
import com.phung.clothshop.model.dto.account.AccountRegisterReqDTO;
import com.phung.clothshop.model.dto.account.AccountResDTO;
import com.phung.clothshop.model.order.Cart;
import com.phung.clothshop.service.JwtService;
import com.phung.clothshop.service.account.IAccountService;
import com.phung.clothshop.service.cart.ICartService;
import com.phung.clothshop.service.customer.ICustomerService;
import com.phung.clothshop.utils.AppUtils;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("api/auth")
public class AuthAPI {

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private ICartService iCartService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated AccountLoginReqDTO accountLoginReqDTO,
            BindingResult bindingResult,
            HttpServletRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountLoginReqDTO.getUsername(),
                        accountLoginReqDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);

        // debug mấy cái khác để xem có thế lấy usernaem từ đó
        Account curentAccount = iAccountService.getByUsername(accountLoginReqDTO.getUsername());

        if (curentAccount.getDeleted()) {
            bindingResult.rejectValue("account", "deleted", "Your account has been deleted");
        }

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        try {

            Optional<Customer> customerOptional = iCustomerService.findByAccount(curentAccount);
            Customer customer = customerOptional.get();
            Long customerId = customer.getId();
            Long cartId = customer.getCart().getId();
            HttpSession session = request.getSession(true);
            session.setAttribute("CUSTOMER_ID", customerId);
            session.setAttribute("CART_ID", cartId);

            ResponseCookie responseCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(jwtService.JWT_TOKEN_VALIDITY)
                    .domain(appUtils.DOMAIN_SERVER)
                    .build();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body("Login successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.rejectValue("account", "data", "Invalid data! Please check again!");
            return new ResponseEntity<>(bindingResult, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated AccountRegisterReqDTO accountRegisterReqDTO,
            BindingResult bindingResult) throws NumberFormatException, ParseException {

        Boolean existByUserName = iAccountService.existsByUsername(accountRegisterReqDTO.getUsername());

        if (existByUserName) {
            bindingResult.rejectValue("username", "duplicate", "Username already exists");
        }

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        // return new ResponseEntity<>("Account and customer saved successfully!",
        // HttpStatus.CREATED);

        Account account = iAccountService.save(accountRegisterReqDTO.toAccount());
        Cart cart = new Cart();
        Cart cartSave = iCartService.save(cart);

        Customer customer = accountRegisterReqDTO.toCustomer();
        customer.setAccount(account);
        customer.setCart(cartSave);

        iCustomerService.save(customer);

        return new ResponseEntity<>("Account and customer saved successfully!", HttpStatus.CREATED);

    }
}
