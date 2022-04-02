package com.enclave.backend.config;

import com.enclave.backend.jwt.JwtAuthenticationFilter;
import com.enclave.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EmployeeService employeeService;

    @Bean
    public static BCryptPasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(employeeService);
        auth.setPasswordEncoder(passwordEncode());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        super.configure(auth);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/login").permitAll()
//                .antMatchers("/api/").authenticated()
//                .antMatchers("/api/branch/").hasAnyAuthority("OWNER")
                .antMatchers("/api/branch/*").hasAuthority("OWNER")
                .antMatchers("/api/category/*").hasAuthority("OWNER")
                .antMatchers("/api/product/*").hasAuthority("OWNER")
                .antMatchers("/api/discount/*").hasAnyAuthority("OWNER")
                .antMatchers("/api/employee/*").hasAnyAuthority("MANAGER", "OWNER")
                .antMatchers("/api/employee/{id}/all").hasAuthority("MANAGER")
                .antMatchers("/api/employee/{id}").hasAuthority("SELLER")
                .antMatchers("/api/order/all").hasAnyAuthority("MANAGER")
                .antMatchers("/api/order/new").hasAuthority("SELLER")
                .antMatchers("/api/order/{id}").hasAuthority("SELLER")

                .and().logout();
//        http.cors();
        http.cors().configurationSource(corsConfigurationSource());

        http.csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    //This can be customized as required
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        List<String> allowOrigins = Arrays.asList("*");
//        configuration.setAllowedOrigins(allowOrigins);
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));

        configuration.setAllowedMethods(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        //in case authentication is enabled this flag MUST be set, otherwise CORS requests will fail
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
