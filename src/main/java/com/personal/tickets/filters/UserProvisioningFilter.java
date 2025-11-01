package com.personal.tickets.filters;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.personal.tickets.domain.Entities.User;
import com.personal.tickets.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            if (authentication != null && authentication.isAuthenticated()
                    && authentication.getPrincipal() instanceof Jwt jwt) {

                String subject = jwt.getSubject();
                UUID keycloakId = safeParseUUID(subject);

                if (keycloakId == null) {
                    log.warn("Invalid Keycloak subject format (not UUID): {}", subject);
                    filterChain.doFilter(request, response);
                    return;
                }

                Optional<User> existingUser = userRepository.findById(keycloakId);

                if (existingUser.isEmpty()) {
                    User newUser = User.builder()
                            .id(keycloakId)
                            .name(getClaim(jwt, "name"))
                            .email(getClaim(jwt, "email"))
                            .build();

                    userRepository.save(newUser);
                    log.info("✅ New user provisioned: {} ({})", newUser.getName(), newUser.getEmail());
                }
            }
        } catch (Exception e) {
            log.error("❌ Error in UserProvisioningFilter: {}", e.getMessage(), e);
        }

        // Always continue the filter chain
        filterChain.doFilter(request, response);
    }

    private UUID safeParseUUID(String value) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String getClaim(Jwt jwt, String claimName) {
        Object claim = jwt.getClaim(claimName);
        return claim != null ? claim.toString() : "";
    }
}