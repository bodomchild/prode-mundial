package com.facrod.prodemundial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        ProdeMundialApplication.class,
        Jsr310JpaConverters.class
})
@EnableWebMvc
public class ProdeMundialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdeMundialApplication.class, args);
    }

}
