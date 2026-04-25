package be.boets.addresstool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AddresstoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddresstoolApplication.class, args);
	}

}
