package br.com.monitoramento.dynatrace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableSwagger2
@SpringBootApplication
public class DynatraceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynatraceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

}
