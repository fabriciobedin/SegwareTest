package io.github.fabriciobedin.server.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name = "text")
	private String text;

	@Column(name = "votes")
	private Integer votes;

	public Post() {
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVote(Integer votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", text=" + text + ", votes=" + votes + "]";
	}

}

