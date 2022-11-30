package board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardFileDto;
import board.board.entity.BoardFileEntity;

@Component
public class FileUtils {
	//특정 게시물 번호에 대한 파일 업로드 처리를 하는 메서드
	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		//업로드된 파일이 없으면 처리
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		
		//업로드 파일 정보를 가지는 List 구조 선언
		List<BoardFileDto> fileList = new ArrayList<>();
		
		//현재시간을 원하는 형태의 포맷으로 선언
		//SimpleDateFormat 과 같은 기능
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		//현재 시간을 리턴
		ZonedDateTime current = ZonedDateTime.now();
		//파일 업로드 경로 선언
		String path = "images/" + current.format(format);
		
		//업로드 파일을 경로에 저장하기 위해 선언
		File file = new File(path);
		
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			
			for(MultipartFile multipartFile : list) {
				//업로드 파일이 존재하면 처리
				if(multipartFile.isEmpty() == false) {
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}else {
							break;
						}
					}
					
					//파일 이름 지정
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					//첨부파일 Dto 인스턴스 생성
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
					//업로드 폴더에 복사
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
	
	
	
	
	
	
	
	
	//JPA
	public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
	//업로드된 파일이 없으면 처리
	if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
		return null;
	}
	
	//업로드 파일 정보를 가지는 List 구조 선언
	List<BoardFileEntity> fileList = new ArrayList<>();
	
	//현재시간을 원하는 형태의 포맷으로 선언
	//SimpleDateFormat 과 같은 기능
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
	//현재 시간을 리턴
	ZonedDateTime current = ZonedDateTime.now();
	//파일 업로드 경로 선언
	String path = "images/" + current.format(format);
	
	//업로드 파일을 경로에 저장하기 위해 선언
	File file = new File(path);
	
	if(file.exists() == false) {
		file.mkdirs();
	}
	
	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
	
	String newFileName, originalFileExtension, contentType;
	
	while(iterator.hasNext()) {
		List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
		
		for(MultipartFile multipartFile : list) {
			//업로드 파일이 존재하면 처리
			if(multipartFile.isEmpty() == false) {
				contentType = multipartFile.getContentType();
				if(ObjectUtils.isEmpty(contentType)) {
					break;
				}else {
					if(contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					}else if(contentType.contains("image/png")) {
						originalFileExtension = ".png";
					}else if(contentType.contains("image/gif")) {
						originalFileExtension = ".gif";
					}else {
						break;
					}
				}
				
				//파일 이름 지정
				newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
				
				//첨부파일 Dto 인스턴스 생성
				BoardFileEntity boardFile = new BoardFileEntity();
				boardFile.setFileSize(multipartFile.getSize());
				boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
				boardFile.setStoredFilePath(path + "/" + newFileName);
				boardFile.setCreatorId("admin");
				fileList.add(boardFile);
				
				file = new File(path + "/" + newFileName);
				//업로드 폴더에 복사
				multipartFile.transferTo(file);
			}
		}
	}
	return fileList;
}
	
}
