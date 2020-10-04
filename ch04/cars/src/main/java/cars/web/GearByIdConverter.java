package cars.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cars.Gear;
import cars.data.GearRepository;

@Component
public class GearByIdConverter implements Converter<String, Gear> {

  private GearRepository gearRepo;

  @Autowired
  public GearByIdConverter(GearRepository ingredientRepo) {
    this.gearRepo = ingredientRepo;
  }
  
  @Override
  public Gear convert(String id) {
    Optional<Gear> optionalIngredient = gearRepo.findById(id);
	return optionalIngredient.isPresent() ?
		   optionalIngredient.get() : null;
  }

}
