package com.example.study.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
// 로그인한 사용자를 감시하는 역할
public class LoginUserAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("AdminServer");
    }
}
