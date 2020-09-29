package tacos.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Gear;
import tacos.data.GearRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Gear> {

  private GearRepository ingredientRepo;

  @Autowired
  public IngredientByIdConverter(GearRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }
  
  @Override
  public Gear convert(String id) {
    Optional<Gear> optionalIngredient = ingredientRepo.findById(id);
	return optionalIngredient.isPresent() ?
		   optionalIngredient.get() : null;
  }

}
