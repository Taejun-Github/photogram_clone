package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션 없이도 JpaRepository 상속 때문에 IoC 등록이 자동으로 된다.
public interface UserRepository extends JpaRepository<User, Integer>{
	//<오브젝트, 기본키의 타입>
	
	//JPA Query creation from method names
	User findByUsername(String username);
	
	
}
