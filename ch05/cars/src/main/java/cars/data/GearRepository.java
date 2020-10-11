package cars.data;

import org.springframework.data.repository.CrudRepository;

import cars.Gear;

public interface GearRepository
         extends CrudRepository<Gear, String> {

}
