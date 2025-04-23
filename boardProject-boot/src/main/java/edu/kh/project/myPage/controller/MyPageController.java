package edu.kh.project.myPage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.service.MyPageService;
import oracle.jdbc.proxy.annotation.Post;

/*
 * @SessionAttributes의 역할
 * - Model에 추가된 속성 중 key값이 일치하는 속성을 session scope로 변경
 * - SessionStatus 이용 시 session에 등록된 완료할 대상을 찾는 용도
 * 
 * @SessionAttribute 의 역할 (매개변수에 쓰는것)
 * - Session에 존재하는 값을 Key로 얻어오는 역할
 * */

@Controller
@RequestMapping("myPage")
public class MyPageController {

	@Autowired
	private MyPageService service;
	
	@GetMapping("info")
	public String info(@SessionAttribute("loginMember") Member loginMember,
						Model model) {
		
		// 현재 로그인한 회원의 주소 꺼내옴
		// 현재 로그인한 회원 정보 -> session에 등록된 loginMember
		
		String memberAddress = loginMember.getMemberAddress();
		
		// 주소가 있을 경우
		if(memberAddress != null) {
			
			// 구분자 "^^^"를 기준으로 memberAddress 값을 쪼개 String[]로 반환
			String[] arr = memberAddress.split("\\^\\^\\^"); // ^^^로 쪼갠다
			
			model.addAttribute("postcode", arr[0]);
			model.addAttribute("address", arr[1]);
			model.addAttribute("detailAddress", arr[2]);
		}
		
		return "myPage/myPage-info";
	}
	
	// 프로필 이미지 변경 화면
	@GetMapping("profile")
	public String profile() {
		return "myPage/myPage-profile";
	}
	@GetMapping("changePw")
	public String changePw() {
		return "myPage/myPage-changePw";
	}
	@GetMapping("secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	@GetMapping("fileTest")
	public String fileTest() {
		return "myPage/myPage-fileTest";
	}
	@GetMapping("fileList")
	public String fileList() {
		return "myPage/myPage-fileList";
	}
	
	/** 회원 정보 수정
	 * @param inputMember : 커맨드 객체(@ModelAttribute가 생략된 상태) 제출된 수정된 회원 정보 받아오기
	 * @param loginMember : 로그인한 회원 정보
	 * @param memberAddress : 주소만 따라 받은 String[] 구분자
	 * @param ra : 메세지 보낼라고
	 * @return
	 */
	@PostMapping("info")
	public String updateInfo(Member inputMember,
							@SessionAttribute("loginMember") Member loginMember,
							@RequestParam("memberAddress") String[] memberAddress,
							RedirectAttributes ra) {
		
		// inputMember에 로그인한 회원 번호 추가
		inputMember.setMemberNo(loginMember.getMemberNo());
		// inputMember : 회원번호, 회원 닉네임, 전화번호, 주소
		
		// 회원 정보 수정 서비스 호출
		int result = service.updateInfo(inputMember, memberAddress);
		
		String message = null;
		
		if(result > 0) { // 회원 정보 수정 성공
			
			// loginMember 새로 세팅
			// 방금 변경한 값으로 세팅
			
			// loginMember는 세션에 저장된 로그인한 회원정보가 저장된 객체를 참조하고 있다
			
			// -> loginMember를 수정하면 세션에 저장된 로그인한 회원 정보가 수정된다
			// == 세션 데이터와 DB 데이터를 동기화
			
			loginMember.setMemberNickname(inputMember.getMemberNickname());
			loginMember.setMemberTel(inputMember.getMemberTel());
			loginMember.setMemberAddress(inputMember.getMemberAddress());
			
			message = "회원정보 수정을 성공하였습니다";
		}else {
			
			message = "회원정보 수정이 실패하셨습니다";
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:info";
	}
}
