package tacos;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import tacos.data.CarRepository;
import tacos.data.GearRepository;
import tacos.data.OrderRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private GearRepository gearRepository;

  @MockBean
  private CarRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @Test
  public void testHomePage() throws Exception {
    mockMvc.perform(get("/"))
      .andExpect(status().isOk())
      .andExpect(view().name("home"))
      .andExpect(content().string(
          containsString("Welcome to...Shengyang Zhou Car Tuning Demo")));
  }

}
