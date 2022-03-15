package com.pagamento.microservices.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket produtApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pagamento.microservices"))
				.paths(regex("/.*"))
				.build()
				.apiInfo(metaInfo());
	}
	
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"Pagamento API REST",
				"API REST de pagamento.",
				"1.0",
				"Terms of Service",
				new Contact("Edivaldo Nogueira"," ","edivaldo-nogueira@hotmail.com"),
				"Apache License Version 2.0",
				"https://www.apache.org/license.html", new ArrayList<VendorExtension>()
		);
		
		return apiInfo;
				
		
	};
	
	

}
