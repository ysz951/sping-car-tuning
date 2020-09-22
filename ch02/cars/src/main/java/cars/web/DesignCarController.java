// tag::head[]
package cars.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import cars.Gear;
import cars.Gear.Type;
import cars.Car;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignCarController {

//end::head[]

@ModelAttribute
public void addGearsToModel(Model model) {
	List<Gear> Gears = Arrays.asList(
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
	
	Type[] types = Gear.Type.values();
	for (Type type : types) {
	  model.addAttribute(type.toString().toLowerCase(),
	      filterByType(Gears, type));
	}
}
	
//tag::showDesignForm[]
  @GetMapping
  public String showDesignForm(Model model) {
    model.addAttribute("design", new Car());
    return "design";
  }

//end::showDesignForm[]

/*
//tag::processDesign[]
  @PostMapping
  public String processDesign(Design design) {
    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesign[]
 */

//tag::processDesignValidated[]
  @PostMapping
  public String processDesign(@Valid @ModelAttribute("design") Car design, Errors errors, Model model) {
    if (errors.hasErrors()) {
      return "design";
    }

    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesignValidated[]

//tag::filterByType[]
  private List<Gear> filterByType(
      List<Gear> Gears, Type type) {
    return Gears
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

//end::filterByType[]
// tag::foot[]
}
// end::foot[]
