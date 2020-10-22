package tacos;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository repo,
        UserRepository userRepo, PasswordEncoder encoder, TacoRepository tacoRepo) { // user repo for ease of testing with a built-in user
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        Ingredient flourTortilla = new Ingredient("FLTO", "Subwoofers", Type.WRAP);
        Ingredient cornTortilla = new Ingredient("COTO", "Amplifiers", Type.WRAP);
        Ingredient groundBeef = new Ingredient("GRBF", "Seat harnesses", Type.PROTEIN);
        Ingredient carnitas = new Ingredient("CARN", "Fire extinguishers", Type.PROTEIN);
        Ingredient tomatoes = new Ingredient("TMTO", "Spark plugs", Type.VEGGIES);
        Ingredient lettuce = new Ingredient("LETC", "Mass air flow", Type.VEGGIES);
        Ingredient cheddar = new Ingredient("CHED", "Springs", Type.CHEESE);
        Ingredient jack = new Ingredient("JACK", "Shock absorbers", Type.CHEESE);
        Ingredient salsa = new Ingredient("SLSA", "Rolling resistance", Type.SAUCE);
        Ingredient sourCream = new Ingredient("SRCR", "Handling", Type.SAUCE);
        repo.save(flourTortilla);
        repo.save(cornTortilla);
        repo.save(groundBeef);
        repo.save(carnitas);
        repo.save(tomatoes);
        repo.save(lettuce);
        repo.save(cheddar);
        repo.save(jack);
        repo.save(salsa);
        repo.save(sourCream);
        
        
        userRepo.save(new User("habuma", encoder.encode("password"), 
            "Craig Walls", "123 North Street", "Cross Roads", "TX", 
            "76227", "123-123-1234"));
        
        Taco taco1 = new Taco();
        taco1.setName("Carnivore");
        taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
        tacoRepo.save(taco1);

        Taco taco2 = new Taco();
        taco2.setName("Bovine Bounty");
        taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
        tacoRepo.save(taco2);

        Taco taco3 = new Taco();
        taco3.setName("Veg-Out");
        taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
        tacoRepo.save(taco3);

      }
    };
  }
  
}
