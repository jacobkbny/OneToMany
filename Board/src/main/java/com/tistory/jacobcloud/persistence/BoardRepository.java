package com.tistory.jacobcloud.persistence;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tistory.jacobcloud.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> ,SearchBoardRepository {

		//Board 테이블에서 데이터를 가져올 떄 Member 정보도 같이 가져오는 메서드
		@Query("select b , w from Board b left join b.member w where b.bno = :bno")
		Object getBoardWithMember (@Param("bno") Long bno);
	
		//하나의 글번호를 가지고 게시글을 과 댓글 모두 가져오는 메서드
		//하나의 게시글에 여러 개의 댓글이 있으므로 리턴 타입은 Object[]
		//결과는 게시글 , 댓글 + 게시글 댓글 의 형태로 리턴됨.
		@Query("select b, r from Board b left join Reply r on r.board=b where b.bno=:bno")
		List<Object[]>getBoardWithReply(@Param("bno")Long bno);
		
		//목록보기를 위한 메서드
		@Query(value="select b, w, count(r)"
				+"from Board b left join b.member w left join Reply r on r.board= b group by b",
				countQuery="select count(b) from Board b")
				
		Page<Object[]> getBoardWithReplyCount(Pageable pageable);
		
		//게시글 번호를 가지고 데이터를 찾아오는 메서드
		@Query(value="select b,w,count(r)"
					+"from Board b left join b.member w left join Reply r on r.board=b "
					+"where b.bno = :bno")
			Object getBoardByBno(@Param("bno")Long bno);
		
}
