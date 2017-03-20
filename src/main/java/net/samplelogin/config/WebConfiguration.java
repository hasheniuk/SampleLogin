package net.samplelogin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    private static final String LOGIN_VIEW = "forward:login.html";

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(LOGIN_VIEW);
        registry.addViewController("/login").setViewName(LOGIN_VIEW);
    }
}