package com.example.lab12.Config;

import com.example.lab12.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("api/v1/auth/register").permitAll()
                .requestMatchers("api/v1/auth/login").permitAll()
                .requestMatchers("api/v1/auth/logout").permitAll()
                .requestMatchers("api/v1/auth/update").hasAuthority("USER")
                .requestMatchers("api/v1/auth/get").hasAuthority("ADMIN")
                .requestMatchers("api/v1/auth/delete/**").hasAuthority("ADMIN")
                .requestMatchers("api/v1/blog/get").hasAuthority("ADMIN")
                .requestMatchers("api/v1/blog/add").hasAuthority("USER")
                .requestMatchers("api/v1/blog/update/**").hasAuthority("USER")
                .requestMatchers("api/v1/blog/delete/**").hasAuthority("USER")
                .requestMatchers("api/v1/blog/get-id/**").hasAuthority("USER")
                .requestMatchers("api/v1/blog/get-title/**").permitAll()
                .requestMatchers("api/v1/blog/get-mine").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
