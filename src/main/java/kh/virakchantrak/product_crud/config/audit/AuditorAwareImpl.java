package kh.virakchantrak.product_crud.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return Optional.of("SYSTEM");
        }

        String username = auth.getName();
        if (username == null || "anonymousUser".equals(username)) {
            return Optional.empty();
        }

        return Optional.of(username);
    }
}
