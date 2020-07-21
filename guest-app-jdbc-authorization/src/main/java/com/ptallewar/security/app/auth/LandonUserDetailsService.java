package com.ptallewar.security.app.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LandonUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthGroupRespository authGroupRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);
		if (null == user) {
			throw new UsernameNotFoundException("cannot find username: " + username);
		}
		List<AuthGroup> authGroups = this.authGroupRepository.findByUsername(username);
		return new LandonUserPrincipal(user, authGroups);
	}
}
