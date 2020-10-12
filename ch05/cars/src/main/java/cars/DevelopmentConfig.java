package cars;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import cars.Gear.Type;
import cars.data.GearRepository;
import cars.data.UserRepository;

@Profile("!dev")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner dataLoader(GearRepository repo,
                                      UserRepository userRepo, PasswordEncoder encoder) { // user repo for ease of testing with a built-in user
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
        
        
        userRepo.save(new User("y", encoder.encode("s"),
            "Craig Walls", "123 North Street", "Cross Roads", "TX", 
            "76227", "123-123-1234"));
      }
    };
  }
  
}
