package board.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;
import board.board.repository.JpaBoardRepository;
import board.common.FileUtils;


@Service
public class JpaBoardServiceImpl implements JpaBoardService{
	
	//CrudRepository 인터페이스를 상속받은 인터페이스 선언
	@Autowired
	JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	FileUtils fileUtils;
	
	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}

	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		board.setCreatorId("admin");
		
		List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		
		if(CollectionUtils.isEmpty(list) == false) {
			board.setFileList(list);
		}
		
		jpaBoardRepository.save(board);
	}
	
	//Optional : 자바에서 가장 문제가 되는 NullPointerException 예외처리를 하기 위해 java 1.8 부터 도입된 개념으로 
	//객체의 값을 미리 isPresent() 메서드에 의해 체크하는 것으로 절대 NullPointerException이 발생하지 않는다.
	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
		//매개변수 boardIdx를 가지고 테이블에서 자료를 리턴받아 변수에 대입
		Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
		//객체의 데이터 값이 존재 하는지 여부 체크
		if(optional.isPresent()) {
			//특정 게시물 내역을 optional 에서 가져와 BoardEntity 형태의 참조 변수에 대입
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt() + 1);
			//수정 처리
			jpaBoardRepository.save(board);
			return board;
		}else {
			throw new NullPointerException();
		}
	}

	@Override
	public void deleteBoard(int boardIdx) {
		jpaBoardRepository.deleteById(boardIdx);
	}

	@Override
	public BoardFileEntity selectBoardFileInformation(int boardIdx, int idx) throws Exception {
		BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(boardIdx, idx);
		return boardFile;
	}

}
