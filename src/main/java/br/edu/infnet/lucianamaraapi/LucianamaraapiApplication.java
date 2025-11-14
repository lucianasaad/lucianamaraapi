package br.edu.infnet.lucianamaraapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.edu.infnet.lucianamaraapi.client")
public class LucianamaraapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LucianamaraapiApplication.class, args);
	}

}
