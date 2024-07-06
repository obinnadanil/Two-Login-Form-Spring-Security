package com.obinna.spring.two.form.login.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration  {


    @Configuration
    @EnableWebSecurity
    @Order(1)
    public class Adapter{
        @Autowired
        AccessDeniedHandler accessDeniedHandler;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {

            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

            httpSecurity.securityMatcher("/student*")
                    .authorizeHttpRequests(requestMatcherRegistry ->
                            requestMatcherRegistry.requestMatchers("/studentLogin").permitAll()
                                    .requestMatchers(mvcMatcherBuilder.pattern("/student*"))
                                    .hasAuthority("ROLE_STUDENT")

                                   )
                    .formLogin((httpSecurityFormLoginConfigurer ->
                                    httpSecurityFormLoginConfigurer.loginPage("/studentLogin")
                                    .loginProcessingUrl("/student_login")
                                    .failureUrl("/studentLoginPage?error=loginError")
                                    .defaultSuccessUrl("/studentPage")
                                    )
                            )
                    .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                            .logoutUrl("/student_logout")
                            .deleteCookies("JSESSIONID"))
                    .sessionManagement(httpSecuritySessionManagementConfigurer ->
                            httpSecuritySessionManagementConfigurer.invalidSessionUrl("/studentLogin"))
                    .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler()))
                    .csrf(AbstractHttpConfigurer::disable);

            return httpSecurity.build();
        }


    }

    @Configuration
    @EnableWebSecurity
    @Order(2)
    public class Adapter2{

        @Autowired
        AccessDeniedHandler accessDeniedHandler;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.securityMatcher("/admin*")
                    .authorizeHttpRequests(requestMatcherRegistry ->
                            requestMatcherRegistry
                                    .requestMatchers("/adminLogin").permitAll()
                                    .requestMatchers("/admin*")
                                    .hasAuthority("ROLE_ADMIN"))
                    .formLogin(httpSecurityFormLoginConfigurer ->
                            httpSecurityFormLoginConfigurer.loginPage("/adminLogin")
                                    .loginProcessingUrl("/admin_login")
                                    .failureUrl("/adminLogin?error=loginError")
                                    .defaultSuccessUrl("/adminPage"))
                    .logout(httpSecurityLogoutConfigurer ->
                            httpSecurityLogoutConfigurer.logoutUrl("/logout_admin")
                                    .deleteCookies("JSESSIONID")
                                    .logoutSuccessUrl("/logoutAdmin_success"))
                    .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler()))
                    .sessionManagement(httpSecuritySessionManagementConfigurer ->
                            httpSecuritySessionManagementConfigurer.invalidSessionUrl("/adminLogin"))
                    .csrf(AbstractHttpConfigurer::disable);



            return httpSecurity.build();
        }
    }
   @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return ((request, response, accessDeniedException) -> response.sendRedirect("403"));
    }



}
