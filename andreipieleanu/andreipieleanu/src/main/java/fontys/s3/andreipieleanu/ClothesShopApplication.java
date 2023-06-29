package fontys.s3.andreipieleanu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
public class ClothesShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothesShopApplication.class, args);
	}
}
