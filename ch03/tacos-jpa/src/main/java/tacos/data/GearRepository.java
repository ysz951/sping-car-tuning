package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Gear;

public interface GearRepository
         extends CrudRepository<Gear, String> {

}
