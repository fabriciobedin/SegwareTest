package io.github.fabriciobedin.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import io.github.fabriciobedin.server.model.Post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostControllerIntegrationTest {
     @Autowired
     private TestRestTemplate restTemplate;
     
     private final long POST_ID = 68547;

     @LocalServerPort
     private int port;

     private String getRootUrl() {
         return "http://localhost:" + port + "/api";
     }

     @Test
     public void contextLoads() {

     }

     @Test
     public void stage1_testGetAllPosts() {
     HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/posts",
        HttpMethod.GET, entity, String.class);  
        assertNotNull(response.getBody());
    }
     
	@Test
	public void stage2_testCreatePost() {
	    Post post = new Post();
	    post.setId(POST_ID);
	    post.setText("testeee");
	    post.setVote(12);
	    ResponseEntity<Post> postResponse = restTemplate.postForEntity(getRootUrl() + "/posts/create", post, Post.class);
	    assertNotNull(postResponse);
	    assertNotNull(postResponse.getBody());
	}

    @Test
    public void stage3_testGetPostById() {
        Post post = restTemplate.getForObject(getRootUrl() + "/posts/" + POST_ID, Post.class);
        System.out.println(post.getText());
        assertNotNull(post);
    }

   

    @Test
    public void stage4_testUpdatePost() {
        Post post = restTemplate.getForObject(getRootUrl() + "/posts/" + POST_ID, Post.class);
        post.setText("testeee");
        post.setVote(15);
        restTemplate.put(getRootUrl() + "/employees/" + POST_ID, post);
        Post updatedEmployee = restTemplate.getForObject(getRootUrl() + "/posts/" + POST_ID, Post.class);
        assertNotNull(updatedEmployee);
    }

    @Test
    public void stage5_testDeletePost() {
         Post post = restTemplate.getForObject(getRootUrl() + "/posts/" + POST_ID, Post.class);
         assertNotNull(post);
         restTemplate.delete(getRootUrl() + "/posts/" + POST_ID);
         try {
              post = restTemplate.getForObject(getRootUrl() + "/posts/" + POST_ID, Post.class);
         } catch (final HttpClientErrorException e) {
              assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
         }
    }
}