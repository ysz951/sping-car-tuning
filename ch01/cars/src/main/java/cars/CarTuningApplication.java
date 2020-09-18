package cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   // <1>
public class CarTuningApplication {

  public static void main(String[] args) {
    SpringApplication.run(CarTuningApplication.class, args); // <2>
  }

}
