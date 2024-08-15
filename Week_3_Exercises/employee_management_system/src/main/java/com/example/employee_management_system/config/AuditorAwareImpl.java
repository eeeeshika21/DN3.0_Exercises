package com.example.employee_management_system.config;

import org.springframework.data.domain.AuditorAware;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // For simplicity, we're returning a hardcoded username
        // In a real application, you would get this from your security context
        return Optional.of("system");
    }
}
