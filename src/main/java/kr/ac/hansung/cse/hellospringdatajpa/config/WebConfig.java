package kr.ac.hansung.cse.hellospringdatajpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
public class WebConfig implements WebMvcConfigurer {

 @Override
 public void addViewControllers(ViewControllerRegistry registry)
 {
  registry.addViewController("/").setViewName("redirect:/products");
  registry.addViewController("/login").setViewName("login");
  registry.addViewController("/signup").setViewName("signup");
 }


 @Bean
  public SpringSecurityDialect springSecurityDialect() {
   return new SpringSecurityDialect();
 }
}
