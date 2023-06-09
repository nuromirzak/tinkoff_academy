package ru.tinkoff.edu.java.scrapper;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.tinkoff.edu.java.scrapper.configurations.ApplicationConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
@Log4j2
public class ScrapperApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfiguration config = ctx.getBean(ApplicationConfiguration.class);
        log.info("Application started with config: {}", config);
    }
}
