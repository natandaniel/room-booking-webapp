package fifty.shades.of.blush.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import fifty.shades.of.blush.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>  {

	Optional<User> findByUsername(String username);

}
