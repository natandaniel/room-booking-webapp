package com.natandanielapps.consensysbooking.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.natandanielapps.consensysbooking.services.entities.Employee;

/**
 * API's security policy.
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SpringDataJpaUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(Employee.PASSWORD_ENCODER);
	}

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/js/**", "/built/**", "/css/**", "/img/**", "/webjars/**")
				.permitAll().antMatchers("/user/**").hasRole("USER").anyRequest().authenticated().and().formLogin()
				.loginPage("/login").defaultSuccessUrl("/user/", true).permitAll().and().httpBasic().and().csrf()
				.disable().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout").deleteCookies("auth_code").invalidateHttpSession(true)
				.clearAuthentication(true).permitAll().and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
	}
}