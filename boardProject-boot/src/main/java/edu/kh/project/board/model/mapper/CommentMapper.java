package edu.kh.project.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

	/** 댓글 목록 조회 서비스
	 * @param boardNo
	 * @return
	 */
	List<Comment> select(int boardNo);

	/** 댓글/ 답글 등록 서비스
	 * @param comment
	 * @return
	 */
	int insert(Comment comment);

	/** 댓글 삭제 서비스
	 * @param commentNo
	 * @return
	 */
	int delete(int commentNo);

	/** 댓글 수정 서비스
	 * @param comment
	 * @return
	 */
	int update(Comment comment);

}
