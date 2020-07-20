package com.interview.template.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interview.template.constants.UserConstants;
import com.interview.template.exceptions.BlacklistedUserNameException;
import com.interview.template.exceptions.UserEmailExistsException;
import com.interview.template.exceptions.UserNameExistsException;
import com.interview.template.exceptions.UserNotFoundException;
import com.interview.template.model.RestResponse;
import com.interview.template.model.UserEntity;
import com.interview.template.service.UserService;
import com.interview.template.util.RestUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(UserController.BASE_URL)
@AllArgsConstructor
class UserController {

	static final String BASE_URL = "/api/v1/users";

	private final UserService userService;

	@GetMapping
	public List<UserEntity> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{userId}")
	UserEntity getUser(@PathVariable Long userId) throws UserNotFoundException {
		return userService.getUser(userId);
	}

	@RequestMapping(method = RequestMethod.HEAD, value = "/{userId}")
	void checkExists(@PathVariable Long userId) throws UserNotFoundException {
		userService.checkUserExists(userId);
	}

	@PostMapping(value = "/createuser")
	public ResponseEntity<RestResponse<UserEntity>> createUser(HttpServletRequest request, @RequestBody UserEntity user)
			throws UserNotFoundException, UserNameExistsException, UserEmailExistsException,
			BlacklistedUserNameException {
		UserEntity userentity = userService.createUser(user);
		return RestUtil.successResponse(userentity, UserConstants.USER_CREATION_SUCCESSFULL, HttpStatus.OK);
	}

	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<RestResponse<Object>> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
		userService.deleteUserById(userId);
		return RestUtil.successResponse(userId, UserConstants.USER_DELETION_SUCCESSFULL, HttpStatus.OK);
	}

	@GetMapping("/getuser/{userName}")
	public ResponseEntity<RestResponse<List<UserEntity>>> fetchUser(@PathVariable String userName)
			throws UserNotFoundException {
		List<UserEntity> userDetails = userService.fetchUsers(userName);
		if (userDetails.isEmpty()) {
			return RestUtil.successResponse(userDetails, UserConstants.NO_RECORD_FOUND, HttpStatus.OK);
		}
		return RestUtil.successResponse(userDetails, UserConstants.USER_DETAIL_RETRIEVAL_SUCCESSFULL, HttpStatus.OK);
	}
}
