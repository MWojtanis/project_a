package company.review.demo.experience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ExperienceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExperienceApplication.class, args);
	}

}
