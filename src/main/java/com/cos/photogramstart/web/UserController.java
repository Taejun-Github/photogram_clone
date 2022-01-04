package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id) {
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println(principalDetails.getUser());
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//PrincipalDetails mPrincipalDetails = (PrincipalDetails)auth.getPrincipal();
		//mPrincipalDetails.getUser();
		//@AuthenticationPrincipal 어노테이션 없이 유저 정보를 들고오려면 원래 이렇게 해야 한다.
		
		//model.addAttribute("principal", principalDetails.getUser());
		
		return "user/update";
	}
}