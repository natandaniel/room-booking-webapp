package fifty.shades.of.blush.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fifty.shades.of.blush.entities.User;
import fifty.shades.of.blush.security.AuthenticationRequest;
import fifty.shades.of.blush.security.TokenUtil;

@RestController
@RequestMapping(path = "/api/authenticate")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService customUserDetailsService;

	@PostMapping
	public ResponseEntity<User> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			String username = authenticationRequest.getUsername();
			String password = authenticationRequest.getPassword();

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authentication = this.authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

			List<String> roles = new ArrayList<String>();

			for (GrantedAuthority authority : userDetails.getAuthorities()) {
				roles.add(authority.toString());
			}

			return new ResponseEntity<User>(new User(userDetails.getUsername(), userDetails.getPassword(),
					TokenUtil.createToken(userDetails), HttpStatus.OK, roles.toArray(new String[2])), HttpStatus.OK);

		} catch (BadCredentialsException bce) {
			return new ResponseEntity<User>(new User(), HttpStatus.UNPROCESSABLE_ENTITY);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
