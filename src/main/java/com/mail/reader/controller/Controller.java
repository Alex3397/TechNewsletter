package com.mail.reader.controller;

import com.mail.reader.config.RedisCacheConfig;
import com.mail.reader.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableCaching
@RestController
@AllArgsConstructor
@Import({RedisCacheConfig.class})
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class Controller {
    private EmailService emailService;

    @GetMapping
    @Cacheable("emails")
    private Object getEmails() {
        return emailService.ReadEmails();
    }
}
