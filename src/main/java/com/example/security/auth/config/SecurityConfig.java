package com.example.security.auth.config;

import com.example.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.example.model.Role.*;
import static com.example.model.Permission.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    private final LogoutHandler logoutHandler;





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/**").permitAll()
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user/**").hasAnyRole(USER.name(), ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/user/**")
                            .hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                        .requestMatchers(HttpMethod.POST, "/user/**")
                            .hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/user/**")
                            .hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/user/**")
                            .hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())

                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/admin/**")
                            .hasAuthority(ADMIN_READ.name())
                        .requestMatchers(HttpMethod.POST, "/admin/**")
                            .hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/admin/**")
                            .hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/admin/**")
                            .hasAuthority(ADMIN_DELETE.name())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(401, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendError(403, "Forbidden");
                        })
                )

                .authorizeHttpRequests(requests -> requests
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionFixation().changeSessionId()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            SecurityContextHolder.clearContext();
                        })
                );



        return http.build();
    }
}
