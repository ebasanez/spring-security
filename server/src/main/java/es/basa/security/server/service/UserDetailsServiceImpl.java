package es.basa.security.server.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.basa.security.server.model.User;
import es.basa.security.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-14
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user fond with username " + username);
		}
		return toUserDetails(user);
	}

	private UserDetails toUserDetails(User user) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true,
				List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}

}
