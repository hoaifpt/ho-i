package com.school_medical.school_medical_management_system.config;

import com.school_medical.school_medical_management_system.services.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Enable CORS if required (make sure you have a CORS configuration)
                .and()
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Statless
                                                                                                              // session
                                                                                                              // management
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login", // Public login endpoint
                                "/api/auth/**", // Public auth API
                                "/api/medication-submissions/**", // Medication submissions
                                "/api/medical-checkups/**", // Medical checkups
                                "/login/checkemail", // Email check
                                "/api/healthinfo/**", // Health information
                                "/api/medical-events/**", // Medical events
                                "/api/event-batches/**", // Event batches
                                "/api/vaccinations/**", // Vaccinations
                                "/my-children", // Parent's children info
                                "/api/notifications/send-batch/**", // Notifications batch sending
                                "/api/notifications/consent/**", // Notifications consent
                                "/api/notifications/parent", // Parent notifications
                                "/api/event-supplies/**", // Event supplies
                                "/api/medicalsupply/**", // Medical supplies
                                "/api/appointments/**", // Appointments
                                "/api/payment/**", // Payment
                                "/api/vaccination-history", // Vaccination history
                                "/generate-otp", // OTP generation
                                "/api/otp/**",
                                "/api/students/**",
                                "/api/reports/**",
                                "/api/orders/**",
                                "/api/dashboard/**",
                                "/api/**",
                                "/api/classes/**",
                                "/error",
                                "/api/notifications/send")
                        .permitAll() // Permit all the above public endpoints

                        .requestMatchers("/api/parent-info/**").authenticated() // Require authentication for
                                                                                // parent-info
                        .anyRequest().authenticated() // Any other request needs to be authenticated
                )
                .authenticationProvider(authenticationProvider()) // Custom Authentication Provider
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before
                                                                                        // UsernamePasswordAuthenticationFilter
                .logout(logout -> logout
                        .logoutUrl("/logout") // Logout URL
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()) // Return status 200
                                                                                             // instead of redirecting
                        .invalidateHttpSession(true) // Invalidate session on logout
                        .deleteCookies("JSESSIONID") // Clear session cookies
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // ❗ Chỉ dùng NoOpPasswordEncoder khi phát triển
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(false); // BẮT BUỘC nếu gửi token từ frontend
            }
        };
    }
}