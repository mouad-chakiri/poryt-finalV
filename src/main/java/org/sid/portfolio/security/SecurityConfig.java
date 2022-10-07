package org.sid.portfolio.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired 
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder=passwordEncoder();
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select email as principal,password as credential,active from users where email=?")
		.authoritiesByUsernameQuery("select email as principal,role as role from users where email=?")
		.passwordEncoder(passwordEncoder).rolePrefix("ROLE_");
		
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").defaultSuccessUrl("/successAuth",true);
		http.authorizeHttpRequests().antMatchers("/cours").permitAll();
		http.authorizeHttpRequests().antMatchers("/content").hasRole("USER");
		/*	http.authorizeHttpRequests().antMatchers().hasRole("ADMIN");
		http.authorizeHttpRequests().antMatchers().hasRole("USER");
		http.csrf();*/
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
