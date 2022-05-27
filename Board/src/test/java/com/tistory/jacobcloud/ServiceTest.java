package com.tistory.jacobcloud;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;

import com.tistory.jacobcloud.dto.BoardDTO;
import com.tistory.jacobcloud.dto.PageRequestDTO;
import com.tistory.jacobcloud.dto.PageResultDTO;
import com.tistory.jacobcloud.persistence.ReplyRepository;
import com.tistory.jacobcloud.service.BoardService;

@SpringBootTest
public class ServiceTest {
	@Autowired
	private BoardService boardService;
	
//	@Test
	public void testRegister() {
		BoardDTO dto = BoardDTO.builder().title("test").content("testing").memberEmail("pkb74@gmail.com").build();
		
		Long bno = boardService.register(dto);
			System.out.println("------------"+bno);
		
	}
	
//	@Test
	public void testList() {
	//1페이지 10개
	PageRequestDTO pageRequestDTO = new PageRequestDTO();
	PageResultDTO<BoardDTO, Object[]> result =
	boardService.getList(pageRequestDTO);
	for (BoardDTO boardDTO : result.getDtoList()) {
	System.out.println(boardDTO);
			} 
		}
		
//	@Test
	public void testGetBoard() {
		Long bno = 100L;
		BoardDTO boardDTO = boardService.getBoard(bno);
		System.out.println(boardDTO);
				
	}
//	@Transactional
//	@Test
	public void testDeleteBoard() {
			Long bno = 8L;
			boardService.removeBoardandReply(bno);
			
	}
	@Transactional
	@Modifying
	@Test
	public void testModifyBoard() {
		Long bno = 10L;
			BoardDTO dto = BoardDTO.builder().bno(bno).title("제목 수정했음").content("내용도 수정").build();
				boardService.modifyBoard(dto);
	}
	
	
	}

