package fifty.shades.of.blush.web.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fifty.shades.of.blush.data.ResourceNotFoundException;
import fifty.shades.of.blush.data.UserRepository;
import fifty.shades.of.blush.domain.User;
import fifty.shades.of.blush.security.TokenUtil;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	UserRepository users;
	
	@Autowired
	private UserDetailsService customUserDetailsService;
	
	@GetMapping("/api/authenticatedUser")
	public ResponseEntity<User> getAuthenticatedUser() throws ResourceNotFoundException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUsername = authentication.getName();

		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(authenticatedUsername);
		Optional<User> user =  users.findByUsername(authenticatedUsername);

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.add(authority.toString());
		}

		return new ResponseEntity<User>(new User(user.get().getId(), userDetails.getUsername(), userDetails.getPassword(),
				TokenUtil.createToken(userDetails), HttpStatus.OK, roles.toArray(new String[2])), HttpStatus.OK);

	}

}
