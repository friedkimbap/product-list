package kr.ac.hansung.cse.hellospringdatajpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
  @Autowired
  private UserDetailsService customUserDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  private static final String[] PUBLIC_MATCHERS = {
      "/webjars/**",
      "/css/**",
      "/js/**",
      "/images/**",
      "/about/**",
      "/contact/**",
      "/error/**",
      "/console/**"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.
        authorizeHttpRequests(auth-> auth
            .requestMatchers(PUBLIC_MATCHERS).permitAll()
            .requestMatchers("/", "/home", "/signup").permitAll()
            .requestMatchers("/products").hasRole("USER")
            .requestMatchers("/admin/**","/products/**").hasRole("ADMIN").
            anyRequest().authenticated()
        )
        .formLogin(formLogin -> formLogin.loginPage("/login")
            .defaultSuccessUrl("/products")
            .failureUrl("/login?error")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        )
        .userDetailsService(customUserDetailsService)
        .csrf(AbstractHttpConfigurer::disable);

    return http.build();

  }
}
