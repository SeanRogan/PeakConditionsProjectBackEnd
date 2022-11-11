package com.seanrogandev.peakconditions.security;


import com.seanrogandev.peakconditions.security.filter.AuthenticationFilter;
import com.seanrogandev.peakconditions.security.filter.AuthorizationFilter;
import com.seanrogandev.peakconditions.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final MemberServiceImpl memberDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
        //
        authenticationFilter.setFilterProcessesUrl("/api/login");
         http
                 // Default behaviour is to enable CSRF protection.
                 // We need to override this behaviour for our stateless (no cookies used!) REST endpoints.
                 // https://security.stackexchange.com/questions/166724/should-i-use-csrf-protection-on-rest-api-endpoints
                 // https://stackoverflow.com/questions/27390407/post-request-to-spring-server-returns-403-forbidden
                 .csrf().disable();
        http.sessionManagement()
                //no need for session because jwt auth is stateless
                .sessionCreationPolicy(STATELESS);

        http.authorizeRequests()
                //anyone who is signed up for any tier account can use the daily report endpoint(s)
                .antMatchers("/api/getDailyReport/**").hasAnyAuthority("ROLE_USER_FREE","ROLE_USER_PAID", "ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                //only paid account/admins can use the extended report endpoint(s)
                .antMatchers("/api/getExtendedReport/**").hasAnyAuthority("ROLE_USER_PAID", "ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                //endpoints for accessing and modifying member services and roles is limited to admin roles
                .antMatchers(GET, "/api/members/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                .antMatchers(POST, "/api/roles/**", "/api/members/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                //the home page endpoints are open to anyone,
                //anyone can attempt to authorize with login or a refresh token
                .antMatchers("/" , "/home", "/api/login/**" , "/api/token/refresh/**")
                .permitAll()
                //all other requests require authentication
                .anyRequest()
                .authenticated()
                .and()
                //set the login page url
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                //allow anyone to logout,
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // when someone logs out the cookie should be deleted so they have to log back in manually next time
                .logoutSuccessUrl("/logout.done").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
                .and().httpBasic();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    /**
     * Must be done to work with Spring Boot 2.0.
     * <a href="https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide#authenticationmanager-bean">https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide#authenticationmanager-bean</a>
     *
     * @return the authentication manager bean.
     * @throws Exception if anything unexpected happens.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }
}

