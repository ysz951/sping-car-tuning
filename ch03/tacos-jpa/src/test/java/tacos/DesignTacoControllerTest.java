package tacos;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;
import tacos.web.DesignTacoController;

@RunWith(SpringRunner.class)
@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private List<Ingredient> ingredients;

  private Taco design;

  @MockBean
  private IngredientRepository ingredientRepository;

  @MockBean
  private TacoRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @Before
  public void setup() {
    ingredients = Arrays.asList(
            new Ingredient("SUBW", "Subwoofers", Type.Audio),
            new Ingredient("AMPL", "Amplifiers", Type.Audio),
            new Ingredient("SEHA", "Seat harnesses", Type.Interior),
            new Ingredient("FIEX", "Fire extinguishers", Type.Interior),
            new Ingredient("SPPL", "Spark plugs", Type.Engine),
            new Ingredient("MAFL", "Mass air flow", Type.Engine),
            new Ingredient("SPRI", "Springs", Type.Suspension),
            new Ingredient("SHAB", "Shock absorbers", Type.Suspension),
            new Ingredient("RORE", "Rolling resistance", Type.Tires),
            new Ingredient("HADL", "Handling", Type.Tires)
    );

    when(ingredientRepository.findAll())
        .thenReturn(ingredients);

    when(ingredientRepository.findById("SUBW")).thenReturn(Optional.of(new Ingredient("SUBW", "Subwoofers", Type.Audio)));
    when(ingredientRepository.findById("SPPL")).thenReturn(Optional.of(new Ingredient("SPPL", "Spark plugs", Type.Engine)));
    when(ingredientRepository.findById("RORE")).thenReturn(Optional.of(new Ingredient("RORE", "Rolling resistance", Type.Tires)));

    design = new Taco();
    design.setName("Test Car");

    design.setIngredients(Arrays.asList(
            new Ingredient("SUBW", "Subwoofers", Type.Audio),
            new Ingredient("SPPL", "Spark plugs", Type.Engine),
            new Ingredient("RORE", "Rolling resistance", Type.Tires)
  ));

  }

  @Test
  public void testShowDesignForm() throws Exception {
    mockMvc.perform(get("/design"))
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(model().attribute("audio", ingredients.subList(0, 2)))
            .andExpect(model().attribute("interior", ingredients.subList(2, 4)))
            .andExpect(model().attribute("engine", ingredients.subList(4, 6)))
            .andExpect(model().attribute("suspension", ingredients.subList(6, 8)))
            .andExpect(model().attribute("tires", ingredients.subList(8, 10)));
  }

  @Test
  public void processDesign() throws Exception {
    when(designRepository.save(design))
        .thenReturn(design);

    mockMvc.perform(post("/design")
        .content("name=Test+Car&ingredients=SUBW,SPPL,RORE")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().stringValues("Location", "/orders/current"));
  }

}
