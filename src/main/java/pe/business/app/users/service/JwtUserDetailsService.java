package pe.business.app.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.business.app.users.repository.UsersRepository;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UsersRepository usersRepository;

	public static final String USER_NOT_EXIST="7006";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		pe.business.app.users.repository.entity.User user = usersRepository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
			//throw new ServiceException(USER_NOT_EXIST);
		}
		return new User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}

}