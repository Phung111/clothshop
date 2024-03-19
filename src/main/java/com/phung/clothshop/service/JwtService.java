package com.phung.clothshop.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import com.phung.clothshop.domain.entity.account.Account;
import com.phung.clothshop.domain.entity.account.AccountPrinciple;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.service.account.IAccountService;
import com.phung.clothshop.service.customer.ICustomerService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

@Service
public class JwtService {
    private static final String SECRET_KEY = "GoiTenToiNheBanThanHoiCoToiLuonCungChiaSotDeRoiTaLaiCoThemNiemTin";
    SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public static final long JWT_TOKEN_VALIDITY = 1000L * 60 * 60 * 24;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private ICustomerService iCustomerService;

    public String generateTokenLogin(Authentication authentication) {

        // debug để xem full name là gì
        AccountPrinciple accountPrinciple = (AccountPrinciple) authentication.getPrincipal();

        Long accountId = accountPrinciple.getId();
        Optional<Account> accouOptional = iAccountService.findById(accountId);
        Account account = accouOptional.get();
        Optional<Customer> customerOptional = iCustomerService.findByAccount(account);
        Customer customer = customerOptional.get();
        Long customerId = customer.getId();

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", accountPrinciple.getERole().toString());
        claims.put("customerId", customerId);

        return Jwts.builder()
                .setSubject((accountPrinciple.getUsername()))
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_TOKEN_VALIDITY))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {0} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {0}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {0}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {0}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {0}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
