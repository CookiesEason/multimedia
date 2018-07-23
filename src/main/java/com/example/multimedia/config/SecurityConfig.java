package com.example.multimedia.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author CookiesEason
 * 2018/07/23 14:50
 * Security配置
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().anyRequest()
                .and()
                    .authorizeRequests()
                    .antMatchers("/user/api/register").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }



}
