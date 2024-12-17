package com.yc.Rhythm.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yc.Rhythm.security.jwt.AuthEntryPointJwt;
import com.yc.Rhythm.security.jwt.AuthTokenFilter;
import com.yc.Rhythm.security.service.UserDetailsServiceImpl;



@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

    UserDetailsServiceImpl userDetailsService;
  
    private AuthEntryPointJwt unauthorizedHandler;

    private AuthTokenFilter authTokenFilter;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService ,  AuthEntryPointJwt unauthorizedHandler, AuthTokenFilter authTokenFilter){
      this.userDetailsService = userDetailsService;
      this.unauthorizedHandler = unauthorizedHandler;
      this.authTokenFilter = authTokenFilter;
    }
 
  
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
  
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
  
      return authProvider;
    }
  

  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
      return authConfig.getAuthenticationManager();
    }
  
    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
  
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(csrf -> csrf.disable())
          .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> auth
          .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
          .requestMatchers("/api/admin/**").hasRole("ADMIN")
          .requestMatchers("/api/users/**").hasAnyRole("USER","ADMIN") 
          .anyRequest().authenticated());
  
      http.authenticationProvider(authenticationProvider());
  
      http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
  
      return http.build();
    }
}
