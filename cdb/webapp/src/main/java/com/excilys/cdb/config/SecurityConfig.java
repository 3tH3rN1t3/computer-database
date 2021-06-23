package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("password1")).roles("USER")
//		.and().withUser("user2").password(passwordEncoder().encode("password2")).roles("USER")
//		.and().withUser("admin").password(passwordEncoder().encode("adminPassword")).roles("USER", "ADMIN");
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=? ")
		.authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/api/**").and()
		.authorizeRequests().mvcMatchers("/editComputer").hasRole("ADMIN")
		.mvcMatchers("/deleteComputer").hasRole("ADMIN")
		.mvcMatchers("/dashboard").authenticated()
		.mvcMatchers("/login").permitAll().and()
		.formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/dashboard", false).failureUrl("/login?error=true").and()
		.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");
	}
}
