//package com.example.urs.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.stereotype.Component;
//
//@Configuration
//@Component
//public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable();
//
//        httpSecurity.authorizeRequests()
//                .antMatchers("/**")
//                .hasRole("USER")
//                .and()
//                .httpBasic(Customizer.withDefaults());
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.builder()
//                        .username("urs")
//                        .password(passwordEncoder().encode("urs"))
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}