package edu.kh.project.admin.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.member.model.dto.Member;

@Mapper
public interface AdminMapper {

	/** 관리자 로그인
	 * @param memberEmail
	 * @return
	 */
	Member login(String memberEmail);

	Board maxReadCount();

	Board maxLikeCount();

	Board maxCommentCount();

	List<Member> currentMember();

	List<Member> selectWithdrawnMemberList();

	List<Board> selectDeleteBoardList();

	int restoreMember(int memberNo);

	int restoreBoard(int boardNo);

	int checkEmail(String memberEmail);

	int createAdminAccount(Member member);

	List<Member> adminMember();

}
