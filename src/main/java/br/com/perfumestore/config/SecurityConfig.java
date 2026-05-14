package br.com.perfumestore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa BCrypt, que é o padrão seguro para hashear senhas.
        return new BCryptPasswordEncoder();
    }

    // ← ADICIONAR: Injetar o filtro
    private final JwtRequestFilter jwtRequestFilter;

    // ← ADICIONAR: Construtor com @Lazy
    public SecurityConfig(@Lazy JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        // Regra 1: Configuração para API (Stateless) - Rotas /api/**
        http.securityMatcher("/api/**") // Aplica ESTA regra SOMENTE a /api/**
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sem sessão no servidor
                )

                // ← ADICIONAR: Filtro JWT
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                // Neste ponto, adicionaremos o Filtro JWT posteriormente.

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Endpoint de Login API é público
                        .anyRequest().authenticated() // Todas as outras rotas API exigem autenticação
                );


        return http.build();

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Aplica para tudo que NÃO for /api/** (já capturado pelo filtro acima)
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        // 1. Permite rotas públicas e arquivos estáticos
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login/**", "/error").permitAll()

                        // 2. Protege as rotas do painel
                        .requestMatchers("/admin/**").authenticated()

                        // 3. Qualquer outra requisição (deve ser sempre a ÚLTIMA regra)
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/admin/perfumes", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // Necessário para o console do H2 DB
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                );

        return http.build();
    }

    // ← ADICIONAR: AuthenticationManager como Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Este método deve ser adicionado DENTRO da classe SecurityConfig
//    @Bean
//    public org.springframework.security.core.userdetails.UserDetailsService users(PasswordEncoder passwordEncoder) {
//
//        // Detalhes do usuário de teste
//        org.springframework.security.core.userdetails.UserDetails user =
//                org.springframework.security.core.userdetails.User.builder()
//                        .username("admin")
//                        // A senha 'admin' será codificada pelo BCryptPasswordEncoder
//                        .password(passwordEncoder.encode("admin"))
//                        .roles("USER", "ADMIN") // Roles para uso futuro em autorização
//                        .build();
//
//        // Gerenciador em memória (apenas para testes)
//        return new org.springframework.security.provisioning.InMemoryUserDetailsManager(user);
//
//    }

}