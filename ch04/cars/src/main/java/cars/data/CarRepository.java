package cars.data;

import org.springframework.data.repository.CrudRepository;

import cars.Car;

public interface CarRepository
         extends CrudRepository<Car, Long> {

}
