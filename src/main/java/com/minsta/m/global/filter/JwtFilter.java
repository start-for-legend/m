package com.minsta.m.global.filter;

import com.minsta.m.domain.auth.repository.BlackListRepository;
import com.minsta.m.global.security.exception.TokenExpiredException;
import com.minsta.m.global.security.jwt.TokenParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenParser tokenParser;
    private final BlackListRepository blackListRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = tokenParser.resolveToken(request);

        if (token != null && !token.isBlank()) {
            if (blackListRepository.existsByAccessToken(token)) {
                throw new TokenExpiredException();
            }

            Authentication authentication = tokenParser.authentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
