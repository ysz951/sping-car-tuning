package cars;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cars.Gear.Type;
import cars.data.GearRepository;

@SpringBootApplication
public class CarCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(CarCloudApplication.class, args);
  }

  @Bean
  public CommandLineRunner dataLoader(GearRepository repo) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        repo.save(new Gear("SUBW", "Subwoofers", Type.Audio));
        repo.save(new Gear("AMPL", "Amplifiers", Type.Audio));
        repo.save(new Gear("SEHA", "Seat harnesses", Type.Interior));
        repo.save(new Gear("FIEX", "Fire extinguishers", Type.Interior));
        repo.save(new Gear("SPPL", "Spark plugs", Type.Engine));
        repo.save(new Gear("MAFL", "Mass air flow", Type.Engine));
        repo.save(new Gear("SPRI", "Springs", Type.Suspension));
        repo.save(new Gear("SHAB", "Shock absorbers", Type.Suspension));
        repo.save(new Gear("RORE", "Rolling resistance", Type.Tires));
        repo.save(new Gear("HADL", "Handling", Type.Tires));
      }
    };
  }
  
}
