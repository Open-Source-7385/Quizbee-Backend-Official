package pe.edu.upc.quizbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class QuizBeePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizBeePlatformApplication.class, args);
	}

}
