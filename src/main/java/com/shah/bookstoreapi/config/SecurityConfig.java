package com.shah.bookstoreapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.shah.bookstoreapi.model.Constants.API_V1;
import static com.shah.bookstoreapi.model.Constants.DELETE_BOOK;
import static org.springframework.http.HttpMethod.DELETE;

/**
 * @author NORUL
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        // Using Spring to encode password for you - Not secure
        UserDetails user = User
                .withUsername("user")
                .password((passwordEncoder().encode("password")))
                .roles("USER")
                .build();

        // You encode yourself then insert - value is `password`
        UserDetails admin = User
                .withUsername("admin")
                .password("$2a$09$9j2c1BTj4zMU.oaaSNumhOfgYuK21hfNiDR.H8HoE677Vh3kPsuQC")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * Allow endpoint with specific roles
     * Allow h2 console
     * Secure all other endpoints, including Swagger
     * <a href="https://www.youtube.com/watch?v=awcCiqBO36E&list=PLZV0a2jwt22s5NCKOwSmHVagoDW8nflaC&index=7">...</a>
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/h2-console/**")
                        .disable())
                .authorizeRequests(auth -> auth
//                        .antMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html").permitAll()
                        .antMatchers("/h2-console/**").permitAll()
                        .antMatchers(DELETE, API_V1 + DELETE_BOOK + "/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
