package com.artichourey.insighthub.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name="posts")
@Getter
@Setter
@ToString(exclude = {"user", "category"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long postId;
	@Column(length=100,nullable=false)
	private String title;
	@Column(length=10000)
	private String content;
	private String imageName;
	private LocalDateTime addedDate;
	@ManyToOne
	@JoinColumn(name="user_id")
	
	private User user;
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

}