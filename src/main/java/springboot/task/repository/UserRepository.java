package springboot.task.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import springboot.task.model.User;

public interface UserRepository extends MongoRepository<User, Long>{

}
