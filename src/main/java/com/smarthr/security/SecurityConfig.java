package com.smarthr.security;
import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; import org.springframework.web.cors.*;
import java.util.Arrays; import java.util.List;
@Configuration @EnableMethodSecurity public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwt, @Value("${app.cors.allowed-origins}") String configuredOrigins) throws Exception {
  var allowedOrigins=Arrays.stream(configuredOrigins.split(",")).map(String::trim).filter(origin->!origin.isEmpty()).toList();
  http.csrf(c->c.disable()).cors(c->c.configurationSource(req->{var x=new CorsConfiguration();x.setAllowedOrigins(allowedOrigins);x.setAllowedMethods(List.of("*"));x.setAllowedHeaders(List.of("*"));x.setAllowCredentials(true);return x;}))
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/actuator/health").permitAll()
    .requestMatchers("/api/auth/**").permitAll()
    .requestMatchers("/api/employees/**","/api/attendance/**","/api/payroll/**","/api/performance/**").hasAnyRole("ADMIN","HR")
    .anyRequest().authenticated())
   .addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class); return http.build();
 }
}
