package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;

@SpringBootApplication
public class TacoCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(TacoCloudApplication.class, args);
  }

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository repo) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        repo.save(new Ingredient("SUBW", "Subwoofers", Type.Audio));
        repo.save(new Ingredient("AMPL", "Amplifiers", Type.Audio));
        repo.save(new Ingredient("SEHA", "Seat harnesses", Type.Interior));
        repo.save(new Ingredient("FIEX", "Fire extinguishers", Type.Interior));
        repo.save(new Ingredient("SPPL", "Spark plugs", Type.Engine));
        repo.save(new Ingredient("MAFL", "Mass air flow", Type.Engine));
        repo.save(new Ingredient("SPRI", "Springs", Type.Suspension));
        repo.save(new Ingredient("SHAB", "Shock absorbers", Type.Suspension));
        repo.save(new Ingredient("RORE", "Rolling resistance", Type.Tires));
        repo.save(new Ingredient("HADL", "Handling", Type.Tires));
      }
    };
  }
  
}
