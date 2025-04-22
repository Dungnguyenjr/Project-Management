package com.practice.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class ConfigSwagger {

    @Bean
    public OpenAPI openAPI() {
        Server prodServer = new Server();
        prodServer.setUrl("");
        prodServer.setDescription("Production Server");

        Contact contact = new Contact();
        contact.setEmail("support@bks.center");
        contact.setName("BKS Support Team");
        contact.setUrl("https://bks.center/suppor");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("BKS - Project Management API")
                .version("1.0")
                .contact(contact)
                .description("This is the API documentation for the BKS Project Management system. Use this documentation to understand and test the APIs.")
                .termsOfService("https://www.bezkoder.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(prodServer));
    }
}
