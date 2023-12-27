package com.phung.clothshop.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.phung.clothshop.service.JwtService;
import com.phung.clothshop.service.account.IAccountService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IAccountService iAccountService;

    private String getBearerTokenRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.replace("Bearer ", "");
            }
            return authHeader;
        }

        return null;
    }

    private String getCookieValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String tokenName = "JWT";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private void setAuthentication(HttpServletRequest request, String authorizationValue) {
        if (authorizationValue != null && jwtService.validateJwtToken(authorizationValue)) {

            String username = jwtService.getUserNameFromJwtToken(authorizationValue);
            UserDetails userDetails = iAccountService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String bearerToken = getBearerTokenRequest(request);

            String authorizationCookie = getCookieValue(request);

            setAuthentication(request, bearerToken);
            setAuthentication(request, authorizationCookie);

        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {0}", e);
        }

        filterChain.doFilter(request, response);
    }

}
