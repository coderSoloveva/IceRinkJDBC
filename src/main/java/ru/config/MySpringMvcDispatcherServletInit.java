package ru.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@ComponentScan("ru")
@EnableWebMvc
public class MySpringMvcDispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    //класс конфигурации
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MySpringMvcDispatcherServletInit.class};
    }

    //какие запросы принимает приложение
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
