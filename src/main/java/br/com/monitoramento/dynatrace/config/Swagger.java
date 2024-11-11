package br.com.monitoramento.dynatrace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger {
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.monitoramento.dynatrace"))
          .paths(PathSelectors.ant("/**"))
          .build()
          .apiInfo(getApiInfo());
    }
	
	public ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Api de Monitoramento Dynatrace").version("1.0.0").build();
	}

}
