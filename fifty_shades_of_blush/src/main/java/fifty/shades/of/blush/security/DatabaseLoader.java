package fifty.shades.of.blush.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fifty.shades.of.blush.entities.User;
import fifty.shades.of.blush.repositories.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {
	
	@Autowired
	private final UserRepository userRepository;
	
	public DatabaseLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		PasswordEncoder pe = new BCryptPasswordEncoder();
		
		User user = new User();
		user.setId(1l);
		user.setFirstName("natan");
		user.setLastName("daniel");
		user.setUsername("natandaniel");
		user.setPassword(pe.encode("natandaniel"));
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		
		userRepository.save(user);
		
	}

}
