package com.example.multimedia.config;

import com.example.multimedia.filter.CorsFilter;
import com.example.multimedia.filter.JwtAuthenticationTokenFilter;
import com.example.multimedia.handler.AuthenticationFailureHandler;
import com.example.multimedia.handler.AuthenticationSuccessHandler;
import com.example.multimedia.handler.DeniedHandler;
import com.example.multimedia.handler.LogoutHandle;
import com.example.multimedia.service.impl.SecurityUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.cache.EhCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * @author CookiesEason
 * 2018/07/23 14:50
 * Security配置
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserImpl securityUser;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());

        http.addFilterBefore(corsControllerFilter(), SecurityContextPersistenceFilter.class);


        http
                .requestMatchers().anyRequest()
                .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers(
                            "/js/**","/css/**","/images/**","/fonts/**",
                            "/api/pictures/**",
                            "/api/data/**",
                            "/api/announcements/**",
                            "/api/user/register","/api/user/activateEmail",
                            "/api/search/**","/adminLogin")
                    .permitAll()
                .antMatchers(HttpMethod.GET,"/api/follower/**").permitAll()
                .antMatchers("/api/user/messages","/api/user/messages/count").authenticated()
                .antMatchers(HttpMethod.GET,"/api/user/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/video/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/articles/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/comment/**").permitAll()
                .antMatchers("/api/tags","/api/smallTags","/api/tags/hot").permitAll()
                .antMatchers("/api/articles/report/**", "/api/video/report/**",
                        "/api/video/play/**").permitAll()
                .antMatchers("/api/admin/**","/admin/**").hasAnyRole("ADMIN","VIDEO_ADMIN","IMGTXT_ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/api/user/login")
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .deleteCookies("Bearer")
                    .logoutSuccessHandler(logoutHandle())
                    .permitAll()
                .and()
                    .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUser).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public CorsFilter corsControllerFilter(){
        return new CorsFilter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new AuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new AuthenticationFailureHandler();
    }

    @Bean
    public LogoutHandle logoutHandle(){
        return new LogoutHandle();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new DeniedHandler();
    }

}
