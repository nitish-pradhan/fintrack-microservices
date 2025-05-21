package com.fintrack.auth.service.security;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintrack.auth.domain.User;
import com.fintrack.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MongoUserDetailsServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private MongoUserDetailsService service;

	private Collection<? extends GrantedAuthority> getTestAuthorities() {
		return Collections.singletonList(
				new SimpleGrantedAuthority("ROLE_USER")
		);
	}

	@Test
	void loadUserByUsername_ReturnsValidUserDetails() {
		// 1. Create a properly configured test user
		User testUser = new User();
//		User testUser = new User(
//				"test@email.com",
//				"$2a$10$N9qo8uLOickgx2ZMRZoMy...", // BCrypt encoded password
//				true,  // enabled
//				true,  // accountNonExpired
//				true,  // credentialsNonExpired
//				true,  // accountNonLocked
//				getTestAuthorities()
//		);

		// 2. Configure mock repository
		when(repository.findById(anyString()))
				.thenReturn(Optional.of(testUser));

		// 3. Execute the method under test
		UserDetails userDetails = service.loadUserByUsername("test@email.com");

		// 4. Verify all assertions
		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo("test@email.com");
		assertThat(userDetails.getPassword()).isEqualTo("$2a$10$N9qo8uLOickgx2ZMRZoMy...");
		assertThat(userDetails.isEnabled()).isTrue();
		assertThat(userDetails.isAccountNonExpired()).isTrue();
		assertThat(userDetails.isCredentialsNonExpired()).isTrue();
		assertThat(userDetails.isAccountNonLocked()).isTrue();

		assertThat(userDetails.getAuthorities())
				.hasSize(1)
				.extracting(GrantedAuthority::getAuthority)
				.containsExactly("ROLE_USER");
	}
}