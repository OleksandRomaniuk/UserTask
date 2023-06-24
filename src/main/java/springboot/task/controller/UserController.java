package springboot.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springboot.task.model.User;
import springboot.task.model.UserDTO;
import springboot.task.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id){
		return userService.getUserById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public User createUser(@RequestBody User user){
		return userService.createUser(user);
	}

	@PutMapping("{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UserDTO user){
		return userService.getUserById(id)
				.map(savedUser -> {

					savedUser.setEmail(user.getEmail());
					savedUser.setName(user.getName());
					User updatedUser = userService.updateUser(savedUser);
					return new ResponseEntity<>(updatedUser, HttpStatus.OK);

				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id){

		userService.deleteUser(id);
		return new ResponseEntity<>("User deleted successfully!.", HttpStatus.OK);
	}

}
