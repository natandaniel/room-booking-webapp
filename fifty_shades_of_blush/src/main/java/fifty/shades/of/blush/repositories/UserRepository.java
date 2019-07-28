package fifty.shades.of.blush.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import fifty.shades.of.blush.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>  {

	User findByUsername(String username);

}
