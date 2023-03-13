package org.mintyn.order.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
class SwaggerConfig implements WebMvcConfigurer {
// WEBSITE IS - http://localhost:8881/swagger-ui/

    @Bean
    public Docket swaggerConfig() {
        //Return a prepared Docket Instance
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(orderReportsInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.mintyn.order.report"))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui/")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo orderReportsInfo() {
        return new ApiInfo(
                "Order Report App",
                "API for Order Report Application",
                "1.0-SNAPSHOT",
                "Open source",
                new Contact("Mintyn", "", "hr@mintyn.com"),
                "API License",
                "",
                Collections.emptyList()
        );
    }

}
