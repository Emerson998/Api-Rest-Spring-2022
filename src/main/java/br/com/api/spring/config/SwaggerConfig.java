package br.com.api.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	// localhost:8081/swagger-ui.html

	@Bean
	public Docket configuration() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis((Predicate<RequestHandler>) RequestHandlerSelectors
						.basePackage("br.com.api.spring"))
				.build().apiInfo(informacaoApi());
	}

	private ApiInfo informacaoApi() {
		return new ApiInfoBuilder().title("Api Spring").description("Api Consolidando Conhecimento").version("1.0.0").build();
	}

}
