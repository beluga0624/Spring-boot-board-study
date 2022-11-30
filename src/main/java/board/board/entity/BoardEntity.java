package board.board.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

//해당 클래스가 객체와 테이블 매핑 처리를 하는 클래스임을 선언하는 어노테이션
//제약사항: 필드에 final, enum, interfacem class 사용 불가
//		 기본생성자를 반드시 선언해야 함
@Entity
//테이블 생성과 관련된 어노테이션
@Table(name="t_jpa_board")
@NoArgsConstructor
@Data
public class BoardEntity {
	//해당 테이블의 Primary key 선언
	@Id
	//사용하는 데이터베이스의 특성에 따라 Auto Increment, Sequence 등의 방식을 통해 기본키 생성 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int boardIdx;
	
	//테이블 생성시 해당 컬럼 값이 널값 허용 여부(false = not null)
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String contents;
	
	@Column(nullable = false)
	private int hitCnt = 0;
	
	@Column(nullable = false)
	private String creatorId;
	
	@Column(nullable = false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updaterId;
	
	private String updatedDatetime;
	
	//두 테이블의 관계 선언(1:N)
	//FetchType : JPA가 하나의 Entity를 조회하는 경우 연관관계에 있는 객체를 어떻게 조회하느냐 하는 방식을 결정하는 속성
	//fetch = FetchType.EAGER(즉시 로딩 방식) : BoardEntity 테이블 조회시 BoardFileEntity 테이블도 함께 조회 처리 
	//fetch = FetchType.LAZY(지연 로딩 방식) : BoardEntity 테이블 조회시 BoardFileEntity를 실제 사용 할때 조회처리하는 방식
	
	//cascade : Entity 상태변화시 관련 Entity에 어떻게 전파(적용) 시킬지 선언하는 속성
	//			Transient : 객체를 생성하고 어떠한 작업을 해도 JPA는 그 객체에 대해 변경된 부분을 감지 못하는 옵션
	//			Persistent : 객체를 저장하여 변경된 부분을 JPA가 감지하는 상태로 save() 메서드에 의해 
	//						 DB에 객체에 대한 데이터가 바로 추가되는 것이 아니라 persistent 상태로 보관되다가 후에 저장되는 상태
	//			detached : JPA가 더이상 관리하지 않는 상태로 다시 JPA관련 기능을 사용하려면 persistent 상태로 되돌아 가야 함
	//			Removed : JPA가 관리하는 상태이지만 실제로 commit이 발생해야지만 삭제처리되는 상태
	//			ALL :
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	//Foreign key 선언
	@JoinColumn(name="board_idx")
	private Collection<BoardFileEntity> fileList;
	
	
}
