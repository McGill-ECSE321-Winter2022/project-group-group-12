package ca.mcgill.ecse321.GSSS;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class GsssBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsssBackendApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting() {
		return "Hello world!";
	}

}