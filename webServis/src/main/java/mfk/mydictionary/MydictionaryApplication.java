package mfk.mydictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MydictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydictionaryApplication.class, args);
	}
}
