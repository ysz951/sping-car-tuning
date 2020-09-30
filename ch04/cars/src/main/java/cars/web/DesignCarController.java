package cars.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import cars.Car;
import cars.Gear;
import cars.Gear.Type;
import cars.Order;
import cars.User;
import cars.data.GearRepository;
import cars.data.CarRepository;
import cars.data.UserRepository;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Slf4j
public class DesignCarController {
  
  private final GearRepository ingredientRepo;
  
  private CarRepository tacoRepo;

  private UserRepository userRepo;

  @Autowired
  public DesignCarController(
        GearRepository ingredientRepo,
        CarRepository tacoRepo,
        UserRepository userRepo) {
    this.ingredientRepo = ingredientRepo;
    this.tacoRepo = tacoRepo;
    this.userRepo = userRepo;
  }

  @ModelAttribute(name = "order")
  public Order order() {
    return new Order();
  }
  
  @ModelAttribute(name = "design")
  public Car design() {
    return new Car();
  }
  
  @GetMapping
  public String showDesignForm(Model model, Principal principal) {
    log.info("   --- Designing taco");
    List<Gear> gears = new ArrayList<>();
    ingredientRepo.findAll().forEach(i -> gears.add(i));
    
    Type[] types = Gear.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(), 
          filterByType(gears, type));
    }
    
    String username = principal.getName();
    User user = userRepo.findByUsername(username);
    model.addAttribute("user", user);

    return "design";
  }

  @PostMapping
  public String processDesign(
          @Valid Car car, Errors errors,
          @ModelAttribute Order order) {

    log.info("   --- Saving car");

    if (errors.hasErrors()) {
      return "design";
    }

    Car saved = tacoRepo.save(car);
    order.addDesign(saved);

    return "redirect:/orders/current";
  }

  private List<Gear> filterByType(
          List<Gear> gears, Type type) {
    return gears
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }
  
}