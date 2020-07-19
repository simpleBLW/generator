package com.simpleblw.generator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Web security config.
 *
 * @author <a href="diwad1024@gmail.com">Dawid Pietryga</a>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${admin.password}")
  private String password;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .headers().frameOptions().sameOrigin()
    .and()
      .authorizeRequests()
        .anyRequest().authenticated()
      .and()
        .httpBasic();
    // @formatter:on
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
    authentication.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode(password))
      .authorities("ROLE_ADMIN");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
