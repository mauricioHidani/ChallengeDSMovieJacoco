package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.entities.RoleEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.UserRepository;
import com.devsuperior.dsmovie.tests.UserDetailsProjectionImpl;
import com.devsuperior.dsmovie.tests.UserFactory;
import com.devsuperior.dsmovie.utils.CustomUserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;

	@Mock
	private CustomUserUtil userUtil;

	private String existingUsername;
	private String noExistingUsername;
	private UserEntity userEntity;
	private RoleEntity roleEntity;
	private UserDetailsProjectionImpl userDetailsProjection;

	@BeforeEach
	void setUp() throws Exception {
		existingUsername = UserFactory.createUserEntity().getUsername();
		noExistingUsername = "fernando123";
		userEntity = UserFactory.createUserEntity();
		roleEntity = new RoleEntity(1L, "ROLE_CLIENT");
		userDetailsProjection = new UserDetailsProjectionImpl(userEntity, roleEntity);

		// Expected username in get logged username
		when(userUtil.getLoggedUsername())
				.thenReturn(existingUsername);

		// Expected user in find by username
		when(repository.findByUsername(existingUsername))
				.thenReturn(Optional.of(userEntity));

		// Expected user in search user and roles by username
		when(repository.searchUserAndRolesByUsername(existingUsername))
			.thenReturn(List.of(userDetailsProjection));
	}

	@Test
	@DisplayName("Authenticated Should Return User Entity When User Exists")
	public void authenticatedShouldReturnUserEntityWhenUserExists() {
		var result = service.authenticated();

		verify(userUtil, times(1)).getLoggedUsername();
		verify(repository, times(1)).findByUsername(any());

		assertNotNull(result);
		assertEquals(result.getId(), userEntity.getId());
		assertEquals(result.getName(), userEntity.getName());
		assertEquals(result.getUsername(), userEntity.getUsername());
		assertEquals(result.getPassword(), userEntity.getPassword());
		assertTrue(result.getAuthorities().containsAll(userEntity.getAuthorities()));
	}

	@Test
	@DisplayName("Authenticated Should Throw Username Not Found Exception When User Does Not Exists")
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
	}

	@Test
	@DisplayName("Load User By Username Should Return User Details When User Exists")
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
	}

	@Test
	@DisplayName("Load User By Username Should Throw Username Not Found Exception When User Does Not Exist")
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
	}
}
