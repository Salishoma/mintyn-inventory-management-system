package org.mintyn.sales.inventory.salesinventory.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /*
    Link to swagger api: http://localhost:8881/swagger-ui/index.html
    * */
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Sales inventory App")
                                .description("API for Sales Application")
                                .version("1.0-SNAPSHOT"))
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Github link")
                                .url("https://github.com/Salishoma/mintyn-inventory-management-system"));
    }

    @Bean
    public GroupedOpenApi reportApi() {
        return GroupedOpenApi.builder()
                .group("Sales Inventory")
                .pathsToMatch("/api/v1/products/**", "/api/v1/orders/**")
                .build();
    }
}