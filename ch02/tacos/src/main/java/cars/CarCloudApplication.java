package cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   // <1>
public class CarCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(CarCloudApplication.class, args); // <2>
  }

}
