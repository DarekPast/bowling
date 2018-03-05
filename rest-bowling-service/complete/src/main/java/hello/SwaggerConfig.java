package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
 
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//				.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("hello"))
//                .paths(PathSelectors.any())
                .paths(regex("/bowling.*"))
                .build()
                .apiInfo(infoData());
//	;    
	}
    private ApiInfo infoData() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Bowling Game",
                "0.1",
                "Terms of service",
				"Darek Past; tel. 503 xxx xxx ; dar.past@wp.pl",
//                (String) new Contact("Darek Past", "https://springframework.guru/about/", "dark.past@wp.pl"),
               "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }

}

