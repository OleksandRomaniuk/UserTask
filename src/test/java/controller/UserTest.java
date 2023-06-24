package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import springboot.task.Application;
import springboot.task.controller.UserController;
import springboot.task.model.User;
import springboot.task.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;


@ContextConfiguration(classes= Application.class)
@WebMvcTest(UserController.class)
public class UserTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;



	@Test
	public void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception{

		User user = User.builder()
				.name("Alex")
				.email("Alex")
				.id(1)
				.build();
		given(userService.createUser(any(User.class)))
				.willAnswer((invocation)-> invocation.getArgument(0));


		ResultActions response = mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)));


		response.andDo(print()).
				andExpect(status().isCreated())
				.andExpect(jsonPath("$.name",
						is(user.getName())))
				.andExpect(jsonPath("$.email",
						is(user.getEmail())));

	}
	@Test
	public void givenListOfUsers_whenGetAllUsers_thenReturnUsersList() throws Exception{

		List<User> listOfUsers = new ArrayList<>();
		listOfUsers.add(User.builder().id(1).name("Alex").email("alex@gmail.com").build());
		listOfUsers.add(User.builder().id(2).name("Roma").email("roma@gmail.com").build());
		given(userService.getAllUsers()).willReturn(listOfUsers);

		ResultActions response = mockMvc.perform(get("/api/users"));

		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()",
						is(listOfUsers.size())));

	}

	@Test
	public void givenListOfUserWhenGetAllUsers_thenReturnUsersList() throws Exception{

		List<User> users = new ArrayList<>();
		users.add(User.builder().id(1).name("Alex").email("alex@gmail.com").build());
		users.add(User.builder().id(2).name("Roma").email("roma@gmail.com").build());
		given(userService.getAllUsers()).willReturn(users);

		ResultActions response = mockMvc.perform(get("/api/users"));

		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()",
						is(users.size())));

	}

	// positive scenario - valid user id
	// JUnit test for GET user by id REST API
	@Test
	public void givenUserId_whenGetUserById_thenReturnUserObject() throws Exception{

		long userId = 1;
		User user = User.builder()
				.name("Alex")
				.email("Alex")
				.build();
		given(userService.getUserById(userId)).willReturn(Optional.of(user));

		ResultActions response = mockMvc.perform(get("/api/users/{id}", userId));

		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name", is(user.getName())))
				.andExpect(jsonPath("$.email", is(user.getEmail())));

	}

	// negative scenario - valid user id
	// JUnit test for GET user by id REST API
	@Test
	public void givenInvalidUserId_whenGetUserById_thenReturnEmpty() throws Exception{

		long userId = 1;

		given(userService.getUserById(userId)).willReturn(Optional.empty());

		ResultActions response = mockMvc.perform(get("/api/users/{id}", userId));

		response.andExpect(status().isNotFound())
				.andDo(print());

	}

	// JUnit test for update user REST API - negative scenario
	@Test
	public void givenUpdatedUser_whenUpdateUser_thenReturn404() throws Exception{
		long userId = 1;

		User updatedUser = User.builder()
				.name("Alex2")
				.email("Alex2")
				.build();
		given(userService.getUserById(userId)).willReturn(Optional.empty());
		given(userService.updateUser(any(User.class)))
				.willAnswer((invocation)-> invocation.getArgument(0));

		ResultActions response = mockMvc.perform(put("/api/users/{id}", userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedUser)));


		response.andExpect(status().isNotFound())
				.andDo(print());
	}

	// JUnit test for delete user REST API
	@Test
	public void givenUserId_whenDeleteUser_thenReturn200() throws Exception{
		long userID = 1;
		willDoNothing().given(userService).deleteUser(userID);

		ResultActions response = mockMvc.perform(delete("/api/users/{id}", userID));

		response.andExpect(status().isOk())
				.andDo(print());
	}


}
