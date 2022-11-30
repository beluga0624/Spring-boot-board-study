package board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.mapper.BoardMapper;
import board.common.FileUtils;


@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}
	
	//MultipartHttpServletRequest : 업로드 화면에서 input 태그의 type이 file인 속성의 이름을 가져오는등 업로드 파일 정보를 가짐 
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		//t_board 테이블에 추가
		boardMapper.insertBoard(board);
		
		//특정 게시물 번호에 대한 첨부파일 리스트를 리턴받아 List구조에 대입
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		
		//t_file 테이블에 추가
		if(CollectionUtils.isEmpty(list) == false){
			boardMapper.insertBoardFileList(list);
		}
		
		//파일업로드 정보가 있으면 처리
		//ObjectUtils.isEmpty() : null 체크와 빈문자열 체크를 동시에 처리
//		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
//			Logger log = LoggerFactory.getLogger(this.getClass());
//			//Iterator:리턴되는 자료를 담고 있는 인터페이스
//			//getFileNames()?
//			//input type이 file인 속성의 이름들을 리턴
//			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
//			
//			String name;
//			
//			//다음에 가져올 데이터가 있는지 여부 체크
//			while(iterator.hasNext()) {
//				name = iterator.next();
//				
//				log.debug("file tag name : " + name);
//				
//				//업로드 파일 정보를 List구조의 참조변수에 대입
//				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
//				
//				for(MultipartFile multipartFile : list) {
//					log.debug("start file information");
//					log.debug("file name : " + multipartFile.getOriginalFilename());
//					log.debug("file size : " + multipartFile.getSize());
//					log.debug("file content type : " + multipartFile.getContentType());
//					log.debug("end file information.\n");
//				}
//			}
//		}
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		boardMapper.updateHitCount(boardIdx);
		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		return board;
	}

	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);
	}

	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
	
	

}
