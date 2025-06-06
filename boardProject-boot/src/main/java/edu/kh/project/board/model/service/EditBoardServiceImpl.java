package edu.kh.project.board.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;
import edu.kh.project.board.model.mapper.EditBoardMapper;
import edu.kh.project.common.util.Utility;

@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class EditBoardServiceImpl implements EditBoardService {

	@Autowired
	private EditBoardMapper mapper;
	
	@Value("${my.board.wep-path}")
	private String webPath;
	
	@Value("${my.board.folder-path}")
	private String folderPath;
	
	// 게시글 작성
	@Override
		public int boardInsert(Board inputBoard, List<MultipartFile> images) throws Exception {

		// 1. 게시글 부분을 먼저 BOARD 테이플에 INSERT하기
		// -> INSERT 결과로 작성된 게시글 번호 반환
		int result = mapper.boardInsert(inputBoard);
		
		// result == INSERT 결과 (삽입 성공한 행의 개수 0 or 1)
		
		// 삽입 실패 시
		if(result == 0) {
			return 0;
		}
		
		// 삽입 성공 시
		// 삽입된 게시글의 번호를 변수로 저장
		// mapper.xml에서 <selectKey> 태그를 이용해서 생성된
		// boardNo가 inputBoard에 저장된 상태 == 얕은 복사된 상태
		int boardNo = inputBoard.getBoardNo();
		
		// 2. 업로드된 이미지가 실제로 존재할 경우
		// 업로드된 이미지만 별도로 저장하여
		// BOARD_IMG 테이블에 삽입하는 코드 작성
		
		// 실제 업로드된 이미지의 정보를 모아둘 List 생성
		List<BoardImg> uploadList = new ArrayList();
		
		// images 리스트에서 하나씩 꺼내어 파일이 있는지 검사
		for(int i = 0; i<images.size(); i++) {
			
			// 실제 선택된 파일이 존재하는 경우
			if(!images.get(i).isEmpty()) {
				
				// 원본명 
				String originalName = images.get(i).getOriginalFilename();
				
				// 변경명 
				String rename = Utility.fileRename(originalName);
				
				// 모든값들을 저장할 DTO 생성
				BoardImg img = BoardImg.builder()
									   .imgOriginalName(originalName)
									   .imgRename(rename)
									   .imgPath(webPath)
									   .boardNo(boardNo)
									   .imgOrder(i)
									   .uploadFile(images.get(i))
									   .build();
				
				// 해당 BoardImg를 uploadList에 추가
				uploadList.add(img);
			}
		}
		
		// 선택한 파일이 전부 없을 경우
		if(uploadList.isEmpty()) {
			return boardNo; // 컨트롤러로 현재 제목/상세내용만 삽입되는 boardNo 리턴
		}
		
		// 선택한 파일이 존재할 경우
		// "BOARD_IMG" 테이블에 insert + 서버에 파일 저장
		
		// 삽입된 행의 개수 반환 == uploadList.size()
		result = mapper.insertUploadList(uploadList);
		
		// 다중 INSERT 성공 여부 확인 (uploadList에 저장된 값이 모두 성공적으로 삽입되었는가)
		if(result == uploadList.size()) {
			
			// 서버에 파일 저장
			for(BoardImg img : uploadList) {
				img.getUploadFile().transferTo(new File(folderPath + img.getImgRename()));
				
			}
				
			}else {
				// 부분적으로 삽입 실패
				// -> 전체 서비스 실패로 판단
				// -> 이전에 삽입된 내용 모두 rollback
				
				// rollback을 강제로 하기 위해
				// Exception 강제로 발생 시킨다 (@Transactional이 Exception을 보고 rollback 시켜준다)
				throw new RuntimeException();
			
		}
		
		return boardNo;
		}
	
	
	@Override
	public int boardUpdate(Board inputBoard, List<MultipartFile> images, String deleteOrderList) throws Exception {
		
		// 1. 게시글 부분(제목/내용) 수정
		int result = mapper.boardUpdate(inputBoard);
		
		// 수정 실패 시 바로 리턴
		if(result == 0) return 0;
		
		// 2. 기존 0 -> 삭제된 이미지(deleteOrderList)가 있는 경우
		if(deleteOrderList != null && !deleteOrderList.equals("")) {
			
			Map<String, Object> map = new HashMap();
			map.put("deleteOrderList", deleteOrderList);
			map.put("boardNo", inputBoard.getBoardNo());
			
			result = mapper.deleteImage(map);
			
			// 삭제 실패한 경우 -> 롤백
			if(result == 0) {
				throw new RuntimeException();
			}
			
		}
		
		// 3. 선택한 파일이 존재할 경우
		// 해당 파일 정보만 모아두는 List 생성
		List<BoardImg> uploadList = new ArrayList();
		
		// images List에서 하나씩 꺼내어 파일이 있는지 검사
		for(int i = 0; i < images.size(); i++) {
			
			// 실제 선택된 파일이 존재하는 경우
			if(!images.get(i).isEmpty()) {
				
				// 원본명
				String originalName = images.get(i).getOriginalFilename();
				
				// 변경명
				String rename = Utility.fileRename(originalName);
				
				// 모든 값을 저장할 DTO (BoardImg) 생성
				BoardImg img = BoardImg.builder().imgOriginalName(originalName)
												 .imgRename(rename)
												 .imgPath(webPath)
												 .boardNo(inputBoard.getBoardNo())
												 .imgOrder(i)
												 .uploadFile(images.get(i))
												 .build();
				
				// 해당 BoardImg를 uploadList 추가
				uploadList.add(img);
				
				// 4. 업로드 하려는 이미지 정보를 이용해서
				// 수정 or 삽입 수행
				
				// 1) 기존 0 -> 새 이미지로 변경 -> 수정
				result = mapper.updateImage(img);
				
				if(result == 0) {
					// 수정 실패 == 기존 순서에 이미지가 없다
					// -> 업데이트가 아닌 삽입을 수행해야 한다
					
					// 2) 기존 x -> 새 이미지 추가
					result = mapper.insertImage(img);
					
				}
			}
			
			// 수정 또는 삽입이 실패한 경우
			if(result == 0) throw new RuntimeException();
			
		}
		
		// 선택된 파일이 하나도 존재하지 않을 경우
		if(uploadList.isEmpty()) {
			return result; 
		}
		
		// 선택한 파일이 하나라도 존재하는 경우
		// 서버에 이미지 파일 저장
		
		for(BoardImg img : uploadList) {
			img.getUploadFile().transferTo(new File(folderPath + img.getImgRename()));
		}
		
		return result;
	}
	
	// 게시글 삭제 서비스
	@Override
	public int boardDelete(Map<String, Integer> map) {
		return mapper.boardDelete(map);
	}
	
}
