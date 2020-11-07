package tacos.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Gear;
import tacos.data.GearRepository;

@Component
public class GearByIdConverter implements Converter<String, Gear> {

  private GearRepository gearRepo;

  @Autowired
  public GearByIdConverter(GearRepository ingredientRepo) {
    this.gearRepo = ingredientRepo;
  }
  
  @Override
  public Gear convert(String id) {
    Optional<Gear> optionalGear = gearRepo.findById(id);
	return optionalGear.isPresent() ?
		   optionalGear.get() : null;
  }

}
