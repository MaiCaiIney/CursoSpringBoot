package com.utn.springboot.billeteravirtual.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/public").permitAll() // Permitir acceso sin autenticación a la URL "/public"
//                        .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
//                ).httpBasic(Customizer.withDefaults());
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers(HttpMethod.GET, "/public").permitAll()  // Permitir solo solicitudes GET a /public
//                .requestMatchers(HttpMethod.POST, "/public").authenticated()  // Requerir autenticación para POST en /public
//                .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
//        ).httpBasic(Customizer.withDefaults());
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/swagger-ui/**", "/v3/api-docs*/**", "/h2-console/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/public").permitAll()  // Permitir solo solicitudes GET a /public
//                .requestMatchers(HttpMethod.POST, "/public").authenticated()  // Requerir autenticación para POST en /public
//                .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
//        ).httpBasic(Customizer.withDefaults()).exceptionHandling(exception -> exception
//                .authenticationEntryPoint(restAuthenticationEntryPoint)) // Configurar el punto de entrada de autenticación
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))  // Deshabilitar CSRF solo para la consola H2
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
//        return http.build();
//    }

//    @Bean
//    public UserDetailsManager userDetailsService() {
//        UserDetails user1 = User.withUsername("alumno").password("{noop}pass").build();
//        UserDetails user2 = User.withUsername("profe").password("{noop}profe").build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService());
//        return authenticationManagerBuilder.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs*/**", "/h2-console/**", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/public").permitAll()
                        .requestMatchers(HttpMethod.POST, "/public").authenticated()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/login"));
        return http.build();
    }
}
