package board.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import board.board.dto.BoardDto;
import board.board.service.BoardService;

//REST API : Representational State Transfer         Application Programming Interface
//REST 구조의 제약 조건을 준수하는 애플리케이션 프로그래밍 인터페이스를 말한다.
//@RestController : @Controller + @ResponseBody 
//클라이언트의 요청에 대해 서버의 응답 처리시 데이터를 JSON 형태로 변환하여 응답
@RestController
public class RestBoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	//rest api 방식의 게시물 리스트
	@RequestMapping(value="/api/board", method=RequestMethod.GET)
	public List<BoardDto> openBoardList() throws Exception {
		return boardService.selectBoardList();
	}

	//rest api 방식의 신규 게시물 등록
	@RequestMapping(value="/api/board/write", method=RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception{
		boardService.insertBoard(board, null);
	}
	
	//rest api 방식의 특정 게시물 상세보기
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		return boardService.selectBoardDetail(boardIdx);
	}
	
	//@RequestBody : 클라이언트에서 전송하는 JSON형태의 Http Body의 내용을 Java 객체로 변환시켜주는 역할을 하는 어노테이션
	//rest api 방식의 특정 게시물 수정
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(@RequestBody BoardDto board) throws Exception {
		boardService.updateBoard(board);
		return "redirect:/board";
	}
	
	//rest api 방식의 특정 게시물 삭제
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
}
