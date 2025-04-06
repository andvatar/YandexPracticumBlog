package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Profile("Test")
@EnableWebMvc
@ComponentScan(basePackages = "ru.yandex.practicum.tarasov")
public class IntegrationTestWebConfiguration {
}
