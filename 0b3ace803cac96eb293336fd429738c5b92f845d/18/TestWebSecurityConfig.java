package com.ctrip.framework.apollo.adminservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(99)
public class TestWebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic();
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/").permitAll().and()
        .authorizeRequests().antMatchers("/console/**").permitAll();

    http.headers().frameOptions().disable();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("user").password("").roles("USER");
    auth.inMemoryAuthentication().withUser("apollo").password("").roles("USER", "ADMIN");
    auth.inMemoryAuthentication().withUser("created").password("").roles("TEST");
    auth.inMemoryAuthentication().withUser("updated").password("").roles("TEST");
    auth.inMemoryAuthentication().withUser("deleted").password("").roles("TEST");
  }
}
