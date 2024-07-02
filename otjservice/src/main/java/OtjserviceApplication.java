

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.maantt.otj.otjservice")
public class OtjserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtjserviceApplication.class, args);
	}

}
 