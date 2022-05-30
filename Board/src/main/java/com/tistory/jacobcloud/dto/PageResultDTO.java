package com.tistory.jacobcloud.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResultDTO<DTO,EN> {
	//DTO리스트
	
	private List<DTO> dtoList;
	
	//전체 페이지 개수
	private int totalPage;
	//전체 페이지 번호
	private int page;
	//출력할 페이지 번호 개수
	private int size;
	//시작하는 페이지 번호 와 끝나는 페이지 번호
	private int start, end;
	//이전 페이지 와 다음 페이지 존재 여부
	private boolean prev ,next;
	//페이지 번호 목록
	private List<Integer> pageList;
	
	private void makePageList(Pageable pageable) {
			this.page= pageable.getPageNumber()+1;
			this.size =pageable.getPageSize();
			
			int tempEnd =(int)(Math.ceil(page/10.0))*10;
			start = tempEnd -9;
			prev = start >1;
			
			end = totalPage > tempEnd ? tempEnd : totalPage;
			next = totalPage > tempEnd ;
			pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
	
	public PageResultDTO(Page<EN> result , Function<EN,DTO> fn) {
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		totalPage = result.getTotalPages();
		makePageList(result.getPageable());
	}
	

}
