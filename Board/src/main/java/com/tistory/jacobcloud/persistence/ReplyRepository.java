package com.tistory.jacobcloud.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tistory.jacobcloud.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	@Modifying
	@Query("delete from Reply r where r.board.bno = :bno")
	public void DeleteByBno(@Param("bno")Long bno);

}
