package pe.business.app.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pe.business.app.users.config.JwtTokenUtil;
import pe.business.app.users.controller.exception.ServiceException;
import pe.business.app.users.model.JwtRequest;
import pe.business.app.users.model.JwtResponse;
import pe.business.app.users.service.UsersService;
import pe.business.app.users.util.Constant;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/grb")
public class JwtAuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	 UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	UsersService usersService;

	@Operation(summary = "Generate Token")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {


		if(!isValidPassword(authenticationRequest.getPassword())){
			throw new ServiceException(Constant.CODE_PASSWORD_FORMAT);
		}
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		usersService.updateUser(authenticationRequest.getUsername(), token);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	public  boolean isValidPassword(final String password) {
		Pattern patternPassword    =  Pattern.compile(jwtTokenUtil.getPatternpass());
		Matcher matcher = patternPassword.matcher(password);
		return matcher.matches();
	}
}
