package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity //디비에 테이블을 생성한다.
@Data
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name="subscribe_uk",
						columnNames= {"fromUserId", "toUserId"}
				)
		}
)
public class Subscribe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략을 데이터베이스를 따라간다.
	private int id;
	
	@JoinColumn(name="fromUserId") //이 이름으로 컬럼명을 만드는 것이다.
	@ManyToOne
	private User fromUser; //구독하는 측
	
	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUser; //구독받는 측
	
	private LocalDateTime createDate;
	
	@PrePersist //DB에 insert되기 직전에 자동으로 실행된다.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
