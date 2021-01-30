package com.example.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 설정에 대한 것이 들어간다는 표시
@EnableJpaAuditing // jpa에 대해서 감시를 활성화시키겠다
public class JpaConfig {
}
