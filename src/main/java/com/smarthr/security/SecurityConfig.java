package com.smarthr.security;
import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; import org.springframework.web.cors.*;
import java.util.List;
@Configuration @EnableMethodSecurity public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwt) throws Exception {
  http.csrf(c->c.disable()).cors(c->c.configurationSource(req->{var x=new CorsConfiguration();x.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5174","http://localhost:5175","http://localhost:5176"));x.setAllowedMethods(List.of("*"));x.setAllowedHeaders(List.of("*"));x.setAllowCredentials(true);return x;}))
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**").permitAll()
    .requestMatchers("/api/employees/**","/api/attendance/**","/api/payroll/**","/api/performance/**").hasAnyRole("ADMIN","HR")
    .anyRequest().authenticated())
   .addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class); return http.build();
 }
}
