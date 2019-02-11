package io.github.fabriciobedin.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.fabriciobedin.server.model.Post;
import io.github.fabriciobedin.server.repository.PostRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	PostRepository postRepository;

	@GetMapping("/posts")
	public List<Post> getAllPosts() {
		System.out.println("Get all Posts...");

		List<Post> list = new ArrayList<>();
		Iterable<Post> posts = postRepository.findAllByOrderByIdDesc();
		
		
		posts.forEach(list::add);
		return list;
	}

	@PostMapping("/posts/create")
	public Post createPost(@Valid @RequestBody Post post) {
		System.out.println("Create Post: " + post.getText() + "...");

		return postRepository.save(post);
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<Post> getPost(@PathVariable("id") Long id) {
		System.out.println("Get Post by id...");

		Optional<Post> postData = postRepository.findById(id);
		if (postData.isPresent()) {
			return new ResponseEntity<>(postData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
		System.out.println("Update Post with ID = " + id + "...");

		Optional<Post> postData = postRepository.findById(id);
		if (postData.isPresent()) {
			Post savedPost = postData.get();
			savedPost.setText(post.getText());
			savedPost.setVote(post.getVotes());

			Post updatedPost = postRepository.save(savedPost);
			return new ResponseEntity<>(updatedPost, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
		System.out.println("Delete Post with ID = " + id + "...");

		try {
			postRepository.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>("Post has been deleted!", HttpStatus.OK);
	}
}