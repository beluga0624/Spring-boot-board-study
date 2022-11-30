package board.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;

//서비스 영역: Controller의 요청에 따라 데이터를 가공하고 Controller에게 리턴하는 business logic 부분
//Controller와 DAO 사이의 결합도를 낮추어 상호간의 의존성을 줄이기 위해 사용
public interface BoardService {
	
	List<BoardDto> selectBoardList() throws Exception;
	
	void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	
	void updateBoard(BoardDto board) throws Exception;
	
	void deleteBoard(int boardIdx) throws Exception;
	
	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
}
