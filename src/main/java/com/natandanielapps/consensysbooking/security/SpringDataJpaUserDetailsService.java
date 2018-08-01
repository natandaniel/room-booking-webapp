package com.natandanielapps.consensysbooking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.natandanielapps.consensysbooking.exception.ResourceNotFoundException;
import com.natandanielapps.consensysbooking.model.Employee;
import com.natandanielapps.consensysbooking.repository.EmployeeRepository;

@Component
public class SpringDataJpaUserDetailsService implements UserDetailsService {

	private final EmployeeRepository employees;

	@Autowired
	public SpringDataJpaUserDetailsService(EmployeeRepository employees) {
		this.employees = employees;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = this.employees.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "name", username));
		return new User(employee.getUsername(), employee.getPassword(),
				AuthorityUtils.createAuthorityList(employee.getRoles()));
	}
}