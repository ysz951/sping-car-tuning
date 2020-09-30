package cars.data;

import org.springframework.data.repository.CrudRepository;

import cars.Order;

public interface OrderRepository 
         extends CrudRepository<Order, Long> {

}
