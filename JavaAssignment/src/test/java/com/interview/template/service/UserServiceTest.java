package com.interview.template.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.interview.template.dao.UserDao;
import com.interview.template.exceptions.BlacklistedUserNameException;
import com.interview.template.exceptions.UserEmailExistsException;
import com.interview.template.exceptions.UserNameExistsException;
import com.interview.template.exceptions.UserNotFoundException;
import com.interview.template.model.UserEntity;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserDao userDao;

	private UserEntity user;

	private List<String> test;

	@BeforeEach
	void beforeEach() {
		user = mockUserEntity();
		test = mockBlacklistedName();
		userService = new UserService(userDao, test);
	}

	@Test
	void shouldFindUser() throws UserNotFoundException {
		doReturn(user).when(userDao).findOrDie(1L);
		assertEquals(user, userService.getUser(1L));
	}

	@Test
	public void createUserSuccessTest()
			throws UserNameExistsException, UserEmailExistsException, BlacklistedUserNameException {
		when(userDao.createUser(user)).thenReturn(user);
		UserEntity userDetails = userService.createUser(user);
		assertEquals("john", userDetails.getUsername());
	}
	
	@Test
	public void blackistedNameAdminTest()
			throws UserNameExistsException, UserEmailExistsException, BlacklistedUserNameException {
		user.setUsername("admin");
		assertThrows(BlacklistedUserNameException.class, () -> userService.createUser(user));
	}
	
	@Test
	public void blackistedNameRootTest()
			throws UserNameExistsException, UserEmailExistsException, BlacklistedUserNameException {
		user.setUsername("root");
		assertThrows(BlacklistedUserNameException.class, () -> userService.createUser(user));
	}
	
	@Test
	public void blackistedNameAdministratorTest()
			throws UserNameExistsException, UserEmailExistsException, BlacklistedUserNameException {
		user.setUsername("administrator");
		assertThrows(BlacklistedUserNameException.class, () -> userService.createUser(user));
	}
	
	@Test
	public void userNameExistTest()
			throws UserNameExistsException, UserEmailExistsException, BlacklistedUserNameException {		
		when(userDao.findByUserName(user.getUsername())).thenReturn(mockUserEntityList("john"));
		assertThrows(UserNameExistsException.class, () -> userService.createUser(user));
	}
	
	@Test
	public void emailIdExistTest()
			throws UserNameExistsException, UserEmailExistsException, BlacklistedUserNameException {	
		when(userDao.findByUserName(user.getUsername())).thenReturn(mockUserEntityList("paul"));
		when(userDao.findByEmail(user.getEmail())).thenReturn(mockUserEntityList("paul"));
		assertThrows(UserEmailExistsException.class, () -> userService.createUser(user));
	}

	private UserEntity mockUserEntity() {
		UserEntity user = UserEntity.builder().id(1L).username("john").email("john@interview.my").password("pass").build();
		return user;
	}

	private List<String> mockBlacklistedName() {
		List<String> blacklistedNames = Arrays.asList("admin", "administrator", "root");
		return blacklistedNames;
	}
	
	private List<UserEntity> mockUserEntityList(String userName) {
		List<UserEntity> userDetails = new ArrayList<>();
		user.setUsername(userName);
		userDetails.add(user);
		return userDetails;		
	}
}
