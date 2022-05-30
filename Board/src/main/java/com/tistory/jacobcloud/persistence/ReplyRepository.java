package com.tistory.jacobcloud.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tistory.jacobcloud.model.Board;
import com.tistory.jacobcloud.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	@Modifying
	@Query("delete from Reply r where r.board.bno = :bno")
	public void DeleteByBno(@Param("bno")Long bno);

	//기세글 번호를 이용해서 갯들 목록을 가져오는 메서드
	
	public List<Reply> getRepliesByBoardOrderByRno(Board board);
}
