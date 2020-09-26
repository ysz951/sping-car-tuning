package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Car;

public interface CarRepository
         extends CrudRepository<Car, Long> {

}
