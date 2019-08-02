package fifty.shades.of.blush.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fifty.shades.of.blush.ResourceNotFoundException;
import fifty.shades.of.blush.entities.User;
import fifty.shades.of.blush.repositories.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private final UserRepository users;

	public DatabaseLoader(UserRepository users) {
		this.users = users;
	}

	@Override
	public void run(String... args) throws Exception {

		try {

			users.findByUsername("natandaniel")
					.orElseThrow(() -> new ResourceNotFoundException("User", "username", "natandaniel"));

		} catch (ResourceNotFoundException e) {

			User u000 = new User("natandaniel", "natandaniel", "ROLE_MANAGER", "ROLE_USER");
			users.save(u000);
		}
	}
}
