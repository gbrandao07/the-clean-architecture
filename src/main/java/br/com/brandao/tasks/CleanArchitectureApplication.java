package br.com.brandao.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.brandao.tasks")
public class CleanArchitectureApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanArchitectureApplication.class, args);
	}

}
