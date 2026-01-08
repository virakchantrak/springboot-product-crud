package kh.virakchantrak.product_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.security.core.SpringSecurityCoreVersion;

@SpringBootApplication
public class ProductCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCrudApplication.class, args);
        System.out.println("Spring Security version: " + SpringSecurityCoreVersion.getVersion());
        System.out.println("Spring version: " + SpringVersion.getVersion());
        System.out.println("Spring Boot version: " + SpringBootVersion.getVersion());
    }
}
