package com.rcuk.portal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static org.thymeleaf.util.StringUtils.isEmpty;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select email, password, coalesce(active , false) from user where email=?")
                .authoritiesByUsernameQuery("select email, role from user where email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers(
                        "/invitation/decline/**",
                        "/actuator/**",
                        "/account-forgot-pwd",
                        "/error",
                        "/account-verify",
                        "/assets/**",
                        "/",
                        "/bookings",
                        "/flight",
                        "/flight2",
                        "/contact",
                        "/login",
                        "/logout",
                        "/**-registration",
                        "/**-registration/**",
                        "/ping")
                .permitAll()
                .antMatchers("/admin/**")
                .hasAnyAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/dashboard")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureHandler((request, response, exception) -> {
                    try{
                        if(!isEmpty(request.getParameter("email"))){
                            log.error("Unsuccessful Login: " + request.getParameter("email").substring(0,5));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    response.sendRedirect("/login?error=true");

                })
                .successHandler((request, response, authentication) -> {
                    try{
                        if(!isEmpty(request.getParameter("email"))){
                            log.info("Successful login: " + request.getParameter("email").substring(0,5));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    response.sendRedirect("/dashboard");
                })
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }
}
