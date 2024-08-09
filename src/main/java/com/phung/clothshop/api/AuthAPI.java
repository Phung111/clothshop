package com.phung.clothshop.api;

import java.text.ParseException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.domain.dto.account.AccountLoginReqDTO;
import com.phung.clothshop.domain.dto.account.AccountRegisterReqDTO;
import com.phung.clothshop.domain.dto.account.AccountResDTO;
import com.phung.clothshop.domain.dto.cusomter.CustomerResDTO;
import com.phung.clothshop.domain.dto.order.CartDTO;
import com.phung.clothshop.domain.entity.account.Account;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.order.Cart;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.service.JwtService;
import com.phung.clothshop.service.account.IAccountService;
import com.phung.clothshop.service.cart.ICartService;
import com.phung.clothshop.service.customer.ICustomerService;
import com.phung.clothshop.utils.AppUtils;

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
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountLoginReqDTO.getUsername(),
                        accountLoginReqDTO.getPassword()));
                        

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
        // Lấy danh sách role của người dùng
        // Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

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

            CustomerResDTO customerResDTO = customer.customerResDTO();

            Long cartID = customer.getCart().getId();
            Optional<Cart> cartOptional = iCartService.findById(cartID);
            Cart cart = cartOptional.get();

            CartDTO cartDTO = cart.toCartDTO();


            AccountResDTO accountResDTO = new AccountResDTO();
            accountResDTO
                .setCustomer(customerResDTO)
                .setCart(cartDTO)
                .setJwt(jwt);

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
                    .body(accountResDTO);
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Invalid data!, please check the information again");
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

        try {
            iAccountService.createAccountAndCustomer(accountRegisterReqDTO);

            return new ResponseEntity<>("Account and customer saved successfully!", HttpStatus.CREATED);

        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST,
                    "Account information is not valid, please check the information again");

        }

    }
}
