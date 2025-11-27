package com.example.SagarBlog.Security;

import com.example.SagarBlog.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        String path = request.getServletPath();
        System.out.println("=== JWT Filter Debug ===");
        System.out.println("Request Path: " + path);
        System.out.println("Method: " + request.getMethod());

        // Skip JWT processing for auth endpoints
        if (path.startsWith("/api/auth/") || path.startsWith("/api/public/")||path.startsWith("/api/posts/public/")) {
            System.out.println("Skipping JWT for auth endpoint");
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);
        System.out.println("Token extracted: " + (token != null ? "Present" : "Null"));

        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            String email = tokenProvider.getUsernameFromToken(token); // This is email
            System.out.println("Email from token: " + email);

            // Load fresh user data from database (including current roles)
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            System.out.println("User loaded from DB: " + userDetails.getUsername());
            System.out.println("User authorities: " + userDetails.getAuthorities());

            // Create authentication with fresh roles from database
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication set in SecurityContext");
        } else {
            System.out.println("Token validation failed or token is null");
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}