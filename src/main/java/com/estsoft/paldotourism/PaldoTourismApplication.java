package com.estsoft.paldotourism;

import com.estsoft.paldotourism.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class PaldoTourismApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaldoTourismApplication.class, args);
    }

}
