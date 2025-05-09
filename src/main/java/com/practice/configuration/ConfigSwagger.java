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
        prodServer.setDescription("Server hệ thống quản lý đồ án tốt nghiệp");

        Contact contact = new Contact();
        contact.setEmail("ngvietdung23k1@gmail.com");
        contact.setName("Nguyễn Việt Dũng");
        contact.setUrl("https://github.com/Dungnguyenjr/Project-Management");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Hệ thống quản lý đồ án tốt nghiệp")
                .version("1.0")
                .contact(contact)
                .description("Tài liệu API cho hệ thống quản lý đồ án tốt nghiệp. Hệ thống hỗ trợ sinh viên, giảng viên và quản trị viên trong việc đăng ký, phân công và theo dõi tiến độ đồ án.")
                .termsOfService("https://www.bezkoder.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(prodServer));
    }

}
