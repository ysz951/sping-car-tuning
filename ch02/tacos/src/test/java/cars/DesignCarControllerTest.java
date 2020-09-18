// tag::testShowDesignForm[]
package cars;
import static org.mockito.Mockito.verify;
import static 
    org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static 
    org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import cars.Gear.Type;
import cars.web.DesignCarController;

//tag::testProcessForm[]
@RunWith(SpringRunner.class)
@WebMvcTest(DesignCarController.class)
public class DesignCarControllerTest {
//end::testProcessForm[]

  @Autowired
  private MockMvc mockMvc;
  
  private List<Gear> Gears;

//end::testShowDesignForm[]

  /*
//tag::testProcessForm[]
   ...

//end::testProcessForm[]
 */

//tag::testProcessForm[]
  private Car design;

//end::testProcessForm[]

//tag::testShowDesignForm[]
  @Before
  public void setup() {
    Gears = Arrays.asList(
            new Gear("SUBW", "Subwoofers", Type.Audio),
            new Gear("AMPL", "Amplifiers", Type.Audio),
            new Gear("SEHA", "Seat harnesses", Type.Interior),
            new Gear("FIEX", "Fire extinguishers", Type.Interior),
            new Gear("SPPL", "Spark plugs", Type.Engine),
            new Gear("MAFL", "Mass air flow", Type.Engine),
            new Gear("SPRI", "Springs", Type.Suspension),
            new Gear("SHAB", "Shock absorbers", Type.Suspension),
            new Gear("RORE", "Rolling resistance", Type.Tires),
            new Gear("HADL", "Handling", Type.Tires)
//      new Gear("FLTO", "Flour Tortilla", Type.WRAP),
//      new Gear("COTO", "Corn Tortilla", Type.WRAP),
//      new Gear("GRBF", "Ground Beef", Type.PROTEIN),
//      new Gear("CARN", "Carnitas", Type.PROTEIN),
//      new Gear("TMTO", "Diced Tomatoes", Type.VEGGIES),
//      new Gear("LETC", "Lettuce", Type.VEGGIES),
//      new Gear("CHED", "Cheddar", Type.CHEESE),
//      new Gear("JACK", "Monterrey Jack", Type.CHEESE),
//      new Gear("SLSA", "Salsa", Type.SAUCE),
//      new Gear("SRCR", "Sour Cream", Type.SAUCE)
    );
    
//end::testShowDesignForm[]
    
    design = new Car();
    design.setName("Test Car");
    design.setGears(Arrays.asList("FLTO", "GRBF", "CHED"));
//tag::testShowDesignForm[]
  }

  @Test
  public void testShowDesignForm() throws Exception {
    mockMvc.perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attribute("audio", Gears.subList(0, 2)))
        .andExpect(model().attribute("interior", Gears.subList(2, 4)))
        .andExpect(model().attribute("engine", Gears.subList(4, 6)))
        .andExpect(model().attribute("suspension", Gears.subList(6, 8)))
        .andExpect(model().attribute("tires", Gears.subList(8, 10)));
  }
//end::testShowDesignForm[]

  /*
//tag::testProcessForm[]
   ...

//end::testProcessForm[]
 */
  
//tag::testProcessForm[]
  @Test
  public void processDesign() throws Exception {
    mockMvc.perform(post("/design")
        .content("name=Test+Car&Gears=FLTO,GRBF,CHED")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().stringValues("Location", "/orders/current"));
  }

//tag::testShowDesignForm[]
}
//end::testShowDesignForm[]
//end::testProcessForm[]
