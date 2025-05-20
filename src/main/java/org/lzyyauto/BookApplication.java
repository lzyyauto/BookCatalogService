package org.lzyyauto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author zingliu
 * @date 2025/5/20
 */
@SpringBootApplication
@EntityScan(basePackages = "org.lzyyauto.entity")
public class BookApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}