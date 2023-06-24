package springboot.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@AllArgsConstructor
@Builder
@Document (collection = "UserDB")
public class User {
	
	@Id
	private long id;

    @Indexed(unique=true)
	private String name;
	private String email;

}
