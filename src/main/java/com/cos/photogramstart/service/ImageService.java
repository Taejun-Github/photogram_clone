package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Value("${file.path}") //application.yml에 있는 값을 사용할 때 Value 어노테이션을 사용한다. 
	//yml 파일에 file밑에 path라는 변수가 있는 형태로 쓰여졌으므로 여기에서도 이렇게 가져온다.
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename(); //실제 파일 이름이 여기에 들어가게 된다.
		//문제점은 무엇일까? 이름만 같고 다른 파일이 다시 업로드되면 덮어쓰기된다. 따라서 uuid를 더해줘서 유일한 파일명을 만든다.
		System.out.println("이미지 파일이름: " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		//이렇게 하면 파일 경로가 만들어진다. yml 파일에서 끝에 / 붙여줘야 함에 주의한다.
		
		//통신, I/O -> 예외가 발생 가능하므로 예외처리를 해주어야 한다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
			//첫번째 인자는 파일 경로, 두번째는 실제 이미지 파일을 바이트 형태로, 세번째는 옵션이다.
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
