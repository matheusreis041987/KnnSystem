package com.knnsystem.api.infrastructure.security;


import com.knnsystem.api.model.entity.Perfil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private static final String PAPEL_ADMINISTRADOR = Perfil.ADMINISTRADOR.name();
    private static final String PAPEL_SECRETARIA = Perfil.SECRETARIA.name();

    private static final String PAPEL_SINDICO = Perfil.SINDICO.name();
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity, SecurityFilter securityFilter) throws Exception {


        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,
                                "/auth/api/login",
                                        "/auth/api/redefine").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/usuario/api/**").hasRole(PAPEL_ADMINISTRADOR)
                        .requestMatchers(HttpMethod.GET,
                                "/usuario/api/**").hasRole(PAPEL_ADMINISTRADOR)
                        .requestMatchers(HttpMethod.PUT,
                                "/usuario/api/**").hasRole(PAPEL_ADMINISTRADOR)
                        .requestMatchers(HttpMethod.POST,
                                "/apartamento/api/**").hasRole(PAPEL_ADMINISTRADOR)
                        .requestMatchers(HttpMethod.GET,
                                "/apartamento/api/**").hasRole(PAPEL_ADMINISTRADOR)
                        .requestMatchers(HttpMethod.PUT,
                                "/apartamento/api/**").hasRole(PAPEL_ADMINISTRADOR)
                        .requestMatchers(HttpMethod.GET,
                                "/relatorio/api/**").hasAnyRole(PAPEL_ADMINISTRADOR, PAPEL_SINDICO)
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
