package ednax.dio.santander.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/users/{id}")
                    .access(new WebExpressionAuthorizationManager("hasRole('TEACHER') or #id == authentication.getId()"))
                .requestMatchers(HttpMethod.GET, "/users/{id}/workout-programs/")
                    .access(new WebExpressionAuthorizationManager("hasRole('TEACHER') or #id == authentication.getId()"))
                .requestMatchers(HttpMethod.GET, "/workout-programs/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/workouts/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/exercises/{id}").authenticated()
                .requestMatchers(HttpMethod.POST, "/auth/user/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/teacher/login").permitAll()
                .anyRequest().hasRole("TEACHER")
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
