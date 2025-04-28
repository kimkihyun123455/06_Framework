package edu.kh.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.project.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	List<Map<String, Object>> selectBoardTypeList();

	/** 게시굴 수 조회
	 * @param boardCode
	 * @return
	 */
	int getListCount(int boardCode);

	List<Board> selectBoardList(int boardCode, RowBounds rowBounds);

}
