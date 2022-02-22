package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommentDto {
	@NotEmpty
	private String content;
	@NotNull
	private int imageId;

}
