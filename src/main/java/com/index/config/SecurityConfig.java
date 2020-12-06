package com.index.config;

import com.index.security.JwtAuthenticationEntryPoint;
import com.index.security.JwtAuthorizationTokenFilter;
import com.index.security.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    UserDetailsService userDetailsService;
    JwtAuthenticationEntryPoint unauthorizedHandler;
    JwtTokenUtil jwtTokenUtil;

    private String tokenHeader = "Authorization";


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                // don't create session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll();
//                .antMatchers(HttpMethod.POST,"/api/subjects").hasRole("ADMIN")
//                .antMatchers("/api/user-Subjects").hasRole("ADMIN")
//                .antMatchers("/api/behaviour").hasAnyRole("TEACHER", "ADMIN")
//                .antMatchers("/api/behaviour/*").hasAnyRole("TEACHER, STUDENT, PARENT, ADMIN")
//                .antMatchers("/api/add-Response").hasAnyRole("PARENT", "ADMIN")
//                .antMatchers("/api/deleteUsers/*").hasRole("ADMIN")
//                .antMatchers("/api/parent-children/*").hasAnyRole("PARENT", "ADMIN")
//                .antMatchers("/api/students").hasAnyRole("PARENT", "ADMIN", "TEACHER")
//                .antMatchers("/api/subjects/*").hasAnyRole("TEACHER, STUDENT, PARENT, ADMIN")
//                .antMatchers("/api/user-information/*").hasAnyRole("TEACHER, STUDENT, PARENT, ADMIN")
//                .antMatchers("/api/user-results/*").hasAnyRole("TEACHER, STUDENT, PARENT, ADMIN")
//                .antMatchers(HttpMethod.POST,"/api/users").hasAnyRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/api/deleteUsers/*").hasAnyRole("ADMIN")
//                .antMatchers(HttpMethod.POST,"/api/grades").hasAnyRole("TEACHER, ADMIN")
//                .antMatchers(HttpMethod.GET,"/api/grades/*/subjects").hasAnyRole("TEACHER, PARENT, ADMIN, STUDENT")
//                .antMatchers(HttpMethod.GET,"/api/userGrades/").hasAnyRole("TEACHER, PARENT, ADMIN")
//                .antMatchers(HttpMethod.GET,"/api/auth/presence/*/subjects").hasAnyRole("TEACHER, PARENT, ADMIN, STUDENT")
//                .antMatchers(HttpMethod.POST,"/api/auth/presences").hasAnyRole("TEACHER, ADMIN")
//                .antMatchers("/error").permitAll()
//                .antMatchers("/error/**").permitAll()
//                .antMatchers("/your Urls that dosen't need security/**").permitAll();
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new JwtAuthorizationTokenFilter(userDetailsService(), jwtTokenUtil, tokenHeader), UsernamePasswordAuthenticationFilter.class);
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity
                .ignoring()
                // All of Spring Security will ignore the requests
                .antMatchers("/error/**");
    }
}
