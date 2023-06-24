package springboot.task.service;

import java.util.List;
import java.util.Optional;

import springboot.task.model.User;
import springboot.task.model.UserDTO;

public interface UserService {

	User createUser(User user);
	List<User> getAllUsers();
	Optional<User> getUserById(long productId);
	User updateUser(User user);
	void deleteUser(long id);
}
