package fifty.shades.of.blush.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fifty.shades.of.blush.ResourceNotFoundException;
import fifty.shades.of.blush.repositories.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

	private UserRepository users;

	@Autowired
	public UserRepositoryUserDetailsService(UserRepository users) {
		this.users = users;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		fifty.shades.of.blush.entities.User user = users.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
		
		return new User(user.getUsername(), user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getRoles()));
	}
}
