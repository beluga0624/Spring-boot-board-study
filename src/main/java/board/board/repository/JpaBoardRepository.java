package board.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;

//BoardEntity : 게시물 Entity, Integer : BoardEntity의 id인 boardIdx의 자료형
public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer>{
	List<BoardEntity> findAllByOrderByBoardIdxDesc();
	
	@Query("select file from BoardFileEntity file where board_idx = :boardIdx and idx = :idx")
	BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);
}

