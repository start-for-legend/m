package com.minsta.m.global.security.config;

import com.minsta.m.global.filter.JwtFilter;
import com.minsta.m.global.security.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        http
                .rememberMe().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable();

        http
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());
                //.authenticationEntryPoint(new CustomAuthenticationEntryPointHandler());

        http
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/**").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/auth").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/auth").authenticated()
                .requestMatchers("/leels/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/file").authenticated()
                .requestMatchers("/chat/**").permitAll() //TODO: Stomp security
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers("/sub/**").permitAll() //TODO: Stomp security
                .requestMatchers("/pub/**").permitAll() //TODO: Stomp security
                .requestMatchers(HttpMethod.POST, "/room/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/room/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/room/**").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/room/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/notice/**").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/notice/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/follow/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/follow/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/follow/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/story/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/story/**").authenticated()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/feed/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/search/**").authenticated()
                .anyRequest().denyAll();

        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
