package learn.bgspr5.ch06;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"learn.bgspr5.ch06"})
public class GatewayAppWebConfig implements WebMvcConfigurer {
}
