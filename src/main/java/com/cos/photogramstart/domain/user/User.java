package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API 자바로 데이터를 영구적으로 저장할 수 있는 api를 제공한다.

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity //디비에 테이블을 생성한다.
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략을 데이터베이스를 따라간다.
	private int id;
	
	@Column(unique = true, length = 20)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website;
	private String bio;
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl;
	private String role;
	
	//나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지 마라. 
	//그리고 User를 셀렉트할 때 해당 User id로 등록된 image들을 다 가져와라.
	//기본값이 LAZY이다. LAZY일 때는 유저를 select할 때 해당 User id로 등록된 image를 가져오지 않는다. 대신 getImages() 함수가 호출될 때 가져올 수 있다.
	//EAGER이면 User select시 해당 User id로 등록된 image들을 전부 Join해서 가져온다.
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY) 
	@JsonIgnoreProperties({"user"}) //이렇게 하면 Image 내에서 user를 무시하고 파싱한다.
	private List<Image> images;
	
	private LocalDateTime createDate;
	
	@PrePersist //DB에 insert되기 직전에 자동으로 실행된다.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role + ", createDate=" + createDate + "]";
	}
	
}

/*
 API(Application Programming Interface 애플리케이션 프로그래밍 인터페이스[*], 응용 프로그램 프로그래밍 인터페이스)는 컴퓨터나 컴퓨터 프로그램 사이의 연결이다. 
 일종의 소프트웨어 인터페이스이며 다른 종류의 소프트웨어에 서비스를 제공한다.[1] 
 이러한 연결이나 인터페이스를 빌드하거나 사용하는 방법을 기술하는 문서나 표준은 API 사양으로 부른다. 
 이 표준을 충족하는 컴퓨터 시스템은 API가 구현(implement)되었다거나 노출(expose)되었다고 말한다. API라는 용어는 사양이나 구현체를 의미할 수 있다.

컴퓨터와 인간을 연결시키는 사용자 인터페이스와 반대로, API는 컴퓨터나 소프트웨어를 서로 연결한다. 
직접 사람(최종 사용자)에 의해 사용되도록 고안된 것이 아니며, 대신 소프트웨어에 이를 통합하고자 하는 컴퓨터 프로그래머가 사용하도록 고안되었다. 
API는 각기 다른 부분으로 구성되기도 하며 프로그래머가 사용할 수 있는 도구나 서비스의 역할을 한다. 
이러한 부분들 중 하나를 사용하는 프로그램이나 프로그래머는 API의 해당 부분을 호출(call)한다고 말한다. 
API를 구성하는 호출들은 서브루틴, 메소드(method), 요청, 통신 엔드포인트라고도 부른다. 
API 사양은 이 호출들을 정의하며, 다시 말해 이들을 어떻게 사용하거나 구현하는지를 설명한다는 것을 의미한다.

API의 한 가지 목적은 시스템이 동작하는 방식에 관한 내부의 세세한 부분을 숨기는 것으로, 
내부의 세세한 부분이 나중에 변경되더라도 프로그래머가 유용하게 사용할 수 있고 일정하게 관리할 수 있는 부분들만 노출시킨다. 
API는 특정 시스템용으로 커스텀하게 빌드될 수도 있고, 아니면 수많은 시스템 간 상호운용성을 허용하는, 공유가 되는 표준일 수도 있다.

API라는 용어는 웹 API를 의미하기도 하며,[2] 이는 인터넷에 의해 병합된 컴퓨터들 간 통신을 허용한다. 
프로그래밍 언어, 소프트웨어 라이브러리, 컴퓨터 운영 체제, 컴퓨터 하드웨어를 위한 API도 존재한다. 
API는 1940년대에 기원하였으나 이 용어는 1960년대, 70년대 들어서야 모습을 드러냈다.
*/