package tacos.web;

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
import tacos.Gear;
import tacos.Gear.Type;
import tacos.Order;
import tacos.Taco;
import tacos.User;
import tacos.data.GearRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Slf4j
public class DesignTacoController {
  
  private final GearRepository ingredientRepo;
  
  private TacoRepository tacoRepo;

  private UserRepository userRepo;

  @Autowired
  public DesignTacoController(
        GearRepository ingredientRepo,
        TacoRepository tacoRepo,
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
  public Taco design() {
    return new Taco();
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
      @Valid Taco taco, Errors errors, 
      @ModelAttribute Order order) {

    log.info("   --- Saving taco");

    if (errors.hasErrors()) {
      return "design";
    }

    Taco saved = tacoRepo.save(taco);
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
