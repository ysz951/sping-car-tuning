package cars.data;
import org.springframework.data.repository.CrudRepository;
import cars.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
