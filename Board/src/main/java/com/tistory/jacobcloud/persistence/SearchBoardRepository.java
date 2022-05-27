package com.tistory.jacobcloud.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tistory.jacobcloud.model.Board;

public interface SearchBoardRepository {
	Board search();
	//3개의 항목 ( Board, Member , Reply)를 Object로 묶어서 리턴하기 ( Object는 모든 클래스를 받을수 있음)
	Page<Object[]> searchPage(String type,String keyword , Pageable pageable);
	
}
