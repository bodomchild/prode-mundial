package com.facrod.prodemundial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        ProdeMundialApplication.class,
        Jsr310JpaConverters.class
})
public class ProdeMundialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdeMundialApplication.class, args);
    }

}
