package com.smj.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
	public Docket api(){
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.smj.order"))
				.build();

		return docket.apiInfo(apiInfo());
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Manage order book API")
				.description("System Manages Order books which consists list of orders for InstrumentId")
				.termsOfServiceUrl("http://smj.com")
				.contact("saimahendrajagarlamudi@gmail.com").license("smj")
				.licenseUrl("saimahendrajagarlamudi@gmail.com").version("1.0")
                .build();
	}
}
