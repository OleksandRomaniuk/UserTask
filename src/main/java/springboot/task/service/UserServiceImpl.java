package springboot.task.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.task.exception.UserNotFoundException;
import springboot.task.model.User;
import springboot.task.model.UserDTO;
import springboot.task.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	private static final String MESSAGE_ALERT = "User not found with id : ";
	
	@Override
	public User createUser(User dto) {
		User user = new User(dto.getId(), dto.getName(), dto.getEmail());
		logger.info("User created");
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User dto) {
		Optional<User> userDb = this.userRepository.findById(dto.getId());
		
		if(userDb.isPresent()) {
			User userUpdate = userDb.get();
			userUpdate.setId(dto.getId());
			userUpdate.setName(dto.getName());
			userUpdate.setEmail(dto.getEmail());
			userRepository.save(userUpdate);
			logger.info("User updated");
			return userUpdate;
		}else {
			logger.error("Fail updating user");
			throw new UserNotFoundException(MESSAGE_ALERT + dto.getId());
		}		
	}

	@Override
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(long productId) {
		
		Optional<User> userDB = this.userRepository.findById(productId);
		
		if(userDB.isPresent()) {
			logger.info("User found");
			return Optional.of(userDB.get());
		}else {
			logger.error("Fail finding user");
			throw new UserNotFoundException(MESSAGE_ALERT + productId);
		}
	}

	@Override
	public void deleteUser(long productId) {
		Optional<User> userDB = this.userRepository.findById(productId);
		
		if(userDB.isPresent()) {
			logger.info("User deleted");
			this.userRepository.delete(userDB.get());
		}else {
			logger.error("Fail deleting user");
			throw new UserNotFoundException(MESSAGE_ALERT + productId);
		}
		
	}

}
