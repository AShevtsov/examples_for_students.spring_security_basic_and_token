package com.students.config;

import com.students.security.filters.JwtTokenBasicAuthenticationFilter;
import com.students.security.jwt.JwtTokenProvider;
import com.students.security.roles.SecurityRole;
import com.students.security.services.UserSpringDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.students.security.filters.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserSpringDetailsService userDetailsService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()
                .antMatchers("/helloAdmin").hasRole("ADMIN")
                .antMatchers("/helloUser").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated();

        //Implementing Token based authentication in this filter
        final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter(jwtTokenProvider, userDetailsService);
        http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);

        //Creating token when basic authentication is successful and the same token can be used to authenticate for further requests
        final JwtTokenBasicAuthenticationFilter customBasicAuthFilter = new JwtTokenBasicAuthenticationFilter(jwtTokenProvider, this.authenticationManager());
        http.addFilter(customBasicAuthFilter);
    }
}
