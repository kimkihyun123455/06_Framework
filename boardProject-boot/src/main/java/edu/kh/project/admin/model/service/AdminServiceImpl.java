package edu.kh.project.admin.model.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.admin.model.mapper.AdminMapper;
import edu.kh.project.board.model.dto.Board;
import edu.kh.project.common.util.Utility;
import edu.kh.project.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	// 관리자 로그인
	@Override
		public Member login(Member inputMember) {
		
			Member loginMember = mapper.login(inputMember.getMemberEmail());
		
			if(loginMember == null) {
				return null;
			}
			
			if(!bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
				return null;
			}
		
			loginMember.setMemberPw(null);
			
			return loginMember;
		}
	
	// 가장 최근에 가입한 회원 정보
	@Override
	public List<Member> currentMember() {
		return mapper.currentMember();
	}
	
	// 최대 조회수 게시글 조회
	@Override
	public Board maxReadCount() {
		return mapper.maxReadCount();
	}
	
	// 최대 좋아요 게시글 조회
	@Override
	public Board maxLikeCount() {
		return mapper.maxLikeCount();
	}
	
	// 최대 댓글 수 게시글 조회
	@Override
	public Board maxCommentCount() {
		return mapper.maxCommentCount();
	}
	
	// 탈퇴한 회원 정보 조회
	@Override
	public List<Member> selectWithdrawnMemberList() {
		return mapper.selectWithdrawnMemberList();
	}
	
	// 삭제된 게시글 조회
	@Override
	public List<Board> selectDeleteBoardList() {
		return mapper.selectDeleteBoardList();
	}
	
	// 탈퇴 회원 복구
	@Override
	public int restoreMember(int memberNo) {
		return mapper.restoreMember(memberNo);
	}
	
	// 삭제된 게시글 복구
	@Override
	public int restoreBoard(int boardNo) {
		return mapper.restoreBoard(boardNo);
	}
	
	// 관리자 이메일 중복 여부 검사
	@Override
	public int checkEmail(String memberEmail) {
		return mapper.checkEmail(memberEmail);
	}
	
	@Override
	public String createAdminAccount(Member member) {
		
		// 1. 숫자를 포함한 영어(대소문자) 6자리 난수로 만든 비밀번호를 암호화
		String rawPw = Utility.generatePassword();
		
		// 2. 평문 비밀번호를 bcrypt를 통해 암호화하여 저장
		String encPw = bcrypt.encode(rawPw);
		
		// 3. member에 암호화된 비밀번호 세팅
		member.setMemberPw(encPw);
		
		// 4. DB에 암호화된 비밀번호가 세팅된 member를 전달하여
		// 	  계정 발급
		int result = mapper.createAdminAccount(member);

		// 5. 계정 발급 정상처리 되었다면 발급된 평문 비밀번호 리턴
		if(result > 0) {
			return rawPw;
		}else {
			return null;
		}
		
	}
	
	@Override
	public List<Member> adminMember() {
		return mapper.adminMember();
	}
}
