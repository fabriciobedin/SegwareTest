package io.github.fabriciobedin.server.repository;

import org.springframework.data.repository.CrudRepository;
import io.github.fabriciobedin.server.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

	Iterable<Post> findAllByOrderByIdDesc(); 
	
}
