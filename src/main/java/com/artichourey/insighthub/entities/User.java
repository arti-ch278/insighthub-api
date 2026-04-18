package com.artichourey.insighthub.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "posts")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="user_name",nullable=false,length=100)
	private String name;
	@Column(nullable = false, unique = true, length = 150)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(length = 500)
	private String about;
	@OneToMany(
	            mappedBy = "user",
	            cascade = CascadeType.ALL,
	            orphanRemoval = true,
	            fetch = FetchType.LAZY
	    )
	private List<Post> posts = new ArrayList<>();

	

}
