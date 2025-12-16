package kh.virakchantrak.product_crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST,"/api/brands").hasAuthority(BRAND_WRITE.getDescription())
//                        .requestMatchers(HttpMethod.GET,"/api/brands").hasAuthority(MODEL_READ.getDescription())
//                        .requestMatchers("/api/models").hasRole(RoleEnum.STAFF.name())

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
