package kh.virakchantrak.product_crud.config;

import kh.virakchantrak.product_crud.common.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemorySecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .authorities(RoleEnum.ADMIN.getAuthorities())
                .build();

        UserDetails staff = User.builder()
                .username("staff")
                .password(passwordEncoder.encode("staff123"))
                .authorities(RoleEnum.STAFF.getAuthorities())
                .build();

        return new InMemoryUserDetailsManager(admin, staff);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
