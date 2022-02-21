package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SubscribeRepository subscribeRepository;

	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {

		UserProfileDto dto = new UserProfileDto();

		// SELECT * FROM image WHERE userId=:userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});

		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId);
		dto.setImageCount(userEntity.getImages().size());

		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

		dto.setSubscribeCount(subscribeCount);
		dto.setSubscribeState(subscribeState == 1); // boolean 값이므로

		userEntity.getImages().forEach((image) -> {
			image.setLikeCount(image.getLikes().size());
		});
		return dto;
	}

	@Transactional
	public User 회원수정(int id, User user) {
		// 1. 영속화
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationApiException("id를 찾을 수 없습니다");
		});
		// get()은 찾은 경우이고 orElseThrow()는 찾지 못해서 예외발생

		// 2. 영속화된 오브젝트를 수정한다.
		userEntity.setName(user.getName());

		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);

		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());

		// 3. 수정된 오브젝트를 반환한다.
		return userEntity;
	}

	
	@Value("${file.path}") 
	private String uploadFolder;
	
	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {
		// TODO Auto-generated method stub
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename(); // 실제 파일 이름이 여기에 들어가게 된다.
		// 문제점은 무엇일까? 이름만 같고 다른 파일이 다시 업로드되면 덮어쓰기된다. 따라서 uuid를 더해줘서 유일한 파일명을 만든다.
		System.out.println("이미지 파일이름: " + imageFileName);

		Path imageFilePath = Paths.get(uploadFolder + imageFileName);

		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
			// 첫번째 인자는 파일 경로, 두번째는 실제 이미지 파일을 바이트 형태로, 세번째는 옵션이다.
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		userEntity.setProfileImageUrl(imageFileName);
		
		return userEntity;
	}
}
