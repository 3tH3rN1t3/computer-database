package com.excilys.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("password1")).roles("USER")
		.and().withUser("user2").password(passwordEncoder().encode("password2")).roles("USER")
		.and().withUser("admin").password(passwordEncoder().encode("adminPassword")).roles("USER", "ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().mvcMatchers("/editComputer").hasRole("ADMIN")
		.mvcMatchers("/dashboard").authenticated()
		.mvcMatchers("/login").permitAll().and()
		.formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/dashboard", false).failureUrl("/login?error=true").and()
		.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");
	}
}
