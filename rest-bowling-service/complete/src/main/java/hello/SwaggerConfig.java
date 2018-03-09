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
import springfox.documentation.builders.ApiInfoBuilder;
 
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
	return new ApiInfoBuilder()
                .title("REST Bowling Game")
                .description("REST Bowling Game Service with Swagger")
                .contact(new Contact("Darek Pastuszka",
                                     "http://nowweb.pl",
                                     "dark.past@wp.pl"))
                .license("License (apache)")
                .licenseUrl("Uhttp://www.apache.org/licenses/LICENSE-2.0")
                .version("0.0.1")
                .build();

    }

}

