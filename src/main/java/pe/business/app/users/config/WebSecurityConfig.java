package pe.business.app.users.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	UserDetailsService jwtUserDetailsService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(this.jwtUserDetailsService).passwordEncoder(bcryptPasswordEncoder());
	}

	@Bean
	public static BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.cors(Customizer.withDefaults());
		httpSecurity.csrf(csrf -> csrf.disable());
		httpSecurity.sessionManagement(sessionManager  -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		httpSecurity.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint));

		// dont authenticate this particular request
		//httpSecurity.authorizeRequests().antMatchers("/grb/authenticate","/grb/swagger-ui.html").permitAll().
				// all other requests need to be authenticated
		//		anyRequest().authenticated().and().
		httpSecurity.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
				.requestMatchers(HttpMethod.GET, "/static/**","/swagger-ui/**", "/swagger-ui/index.html","/swagger-ui.html","swagger-ui/index.html","swagger-ui.html").permitAll()
				.requestMatchers("/actuator/health", "/actuator/metrics", "/actuator/metrics/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/monitor/**","/grb/authenticate","/swagger-ui.html").permitAll()
				.anyRequest().authenticated()
		);
				// make sure we use stateless session; session won't be used to
				// store user's state.
		//httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
		//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
}
