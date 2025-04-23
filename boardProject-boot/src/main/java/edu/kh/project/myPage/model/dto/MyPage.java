package edu.kh.project.myPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPage {

	private int	memberNo; 			// 회원 번호
	private String memberEmail; 	// 회원 이메일
	private String memberPw;		// 회원 비밀번호
	private String memberNickname;	// 회원 닉네임
	private String memberTel;		// 회원 전화번호
	private String memberAddress;	// 회원 주소
	private String profileImg;		// 회원 프로필
	private String enrollDate;		// 회원 가입일
	private String memberDelFl;		// 회원 탈퇴여부('Y'/'N')
	private int authority;			// 권한(1:일반사용자, 2:관리자)
}
