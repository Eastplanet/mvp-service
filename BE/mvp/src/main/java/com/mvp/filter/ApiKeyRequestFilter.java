package com.mvp.filter;

import com.mvp.manager.service.ManagerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class ApiKeyRequestFilter extends OncePerRequestFilter {
    private ManagerService managerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String apiKeyHeader = request.getHeader("API-KEY");

        if(apiKeyHeader != null && managerService.validateApiKey(apiKeyHeader)) {
            String userEmail = managerService.getApiKeyStore().get(apiKeyHeader);
            UsernamePasswordAuthenticationToken authenication = new UsernamePasswordAuthenticationToken(userEmail, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authenication);
        }
        filterChain.doFilter(request, response);
    }
}
