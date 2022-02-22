package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity // 디비에 테이블을 생성한다.
@Data
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략을 데이터베이스를 따라간다.
	private int id;
	private String caption; // 사진에 부가된 메시지
	private String postImageUrl; // 사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장한다. DB에 그 저장된 경로를 insert한다. 그 경로를 String으로.

	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId") //userId라는 이름으로 컬럼을 만든다.
	@ManyToOne
	private User user; // 누가 업로드했는지
	
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;

	private LocalDateTime createDate;
	
	@Transient //DB에 컬럼이 만들어지지 않는다.
	private boolean likeState;
	
	@Transient
	private int likeCount;
	
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image")
	private List<Comment> comments;

	@PrePersist // DB에 insert되기 직전에 자동으로 실행된다.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
