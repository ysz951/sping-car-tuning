package tacos;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import tacos.Gear.Type;
import tacos.data.GearRepository;
import tacos.data.OrderRepository;
import tacos.data.CarRepository;
import tacos.data.UserRepository;
import tacos.web.DesignCarController;

@RunWith(SpringRunner.class)
@WebMvcTest(DesignCarController.class)
public class DesignCarControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private List<Gear> gears;

  private Car design;

  @MockBean
  private GearRepository gearRepository;

  @MockBean
  private CarRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @MockBean
  private UserRepository userRepository;


  @Before
  public void setup() {
    gears = Arrays.asList(
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
    );

    when(gearRepository.findAll())
            .thenReturn(gears);

    when(gearRepository.findById("SUBW")).thenReturn(Optional.of(new Gear("SUBW", "Subwoofers", Type.Audio)));
    when(gearRepository.findById("SPPL")).thenReturn(Optional.of(new Gear("SPPL", "Spark plugs", Type.Engine)));
    when(gearRepository.findById("RORE")).thenReturn(Optional.of(new Gear("RORE", "Rolling resistance", Type.Tires)));

    design = new Car();
    design.setName("Test Car");

    design.setGears(Arrays.asList(
            new Gear("SUBW", "Subwoofers", Type.Audio),
            new Gear("SPPL", "Spark plugs", Type.Engine),
            new Gear("RORE", "Rolling resistance", Type.Tires)
    ));

    when(userRepository.findByUsername("testuser"))
            .thenReturn(new User("testuser", "testpass", "Test User", "123 Street", "Someville", "CO", "12345", "123-123-1234"));
  }

  @Test
  @WithMockUser(username="testuser", password="testpass")
  public void testShowDesignForm() throws Exception {
    mockMvc.perform(get("/design"))
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(model().attribute("audio", gears.subList(0, 2)))
            .andExpect(model().attribute("interior", gears.subList(2, 4)))
            .andExpect(model().attribute("engine", gears.subList(4, 6)))
            .andExpect(model().attribute("suspension", gears.subList(6, 8)))
            .andExpect(model().attribute("tires", gears.subList(8, 10)));
  }

  @Test
  @WithMockUser(username="testuser", password="testpass", authorities="ROLE_USER")
  public void processDesign() throws Exception {
    when(designRepository.save(design))
            .thenReturn(design);

    mockMvc.perform(post("/design").with(csrf())
            .content("name=Test+Car&gears=SUBW,SPPL,RORE")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().stringValues("Location", "/orders/current"));
  }

}
