package fifty.shades.of.blush.repositories;

import org.springframework.data.repository.CrudRepository;

import fifty.shades.of.blush.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
