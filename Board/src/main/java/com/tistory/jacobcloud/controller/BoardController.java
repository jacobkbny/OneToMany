package com.tistory.jacobcloud.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tistory.jacobcloud.dto.BoardDTO;
import com.tistory.jacobcloud.dto.PageRequestDTO;
import com.tistory.jacobcloud.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping({"/","/board/list"})
	public String list(PageRequestDTO pageRequestDTO , Model model) {
		log.info("목록 보기 요청"+pageRequestDTO);
		
		model.addAttribute("result",boardService.getList(pageRequestDTO));
		
		return "/board/list";
	}
	
	@GetMapping("/board/register")
		public void register() {
			log.info("게시물 등록 페이지 요청 ");
	}
	@PostMapping("/board/register")
		public String register(BoardDTO dto,RedirectAttributes rttr) {
			log.info("게시물 등록 "+dto);
			Long bno =boardService.register(dto);
			rttr.addFlashAttribute("msg",bno+"삽입");
			return "redirect:/board/list";
	}
	
	@GetMapping({"/board/read","/board/modify"})
	public void read( Model model,Long bno,@ModelAttribute("requestDTO")PageRequestDTO pageRequestDTO) {
			BoardDTO dto = boardService.getBoard(bno);
				log.info("read html의 dto 입니다"+dto);
			model.addAttribute("dto",dto);
			
			}
	@PostMapping("/board/modify")
	public String modify(BoardDTO dto , @ModelAttribute("reqeustDTO") PageRequestDTO requestDTO,RedirectAttributes rttr) {
				log.info("수정 처리 "+dto);
				log.info("제목"+dto.getTitle());
				log.info("내용"+dto.getContent());
				boardService.modifyBoard(dto);
				rttr.addAttribute("page",requestDTO.getPage()) ;
				rttr.addAttribute("type",requestDTO.getType()) ;
				rttr.addAttribute("keyword",requestDTO.getKeyword()); 
				rttr.addAttribute("bno",dto.getBno()); 
				
				
				//수정후 바로 수정 된 상세보기로 보내기
			return "redirect:/board/read?bno="+dto.getBno()+"&page="+requestDTO.getPage();	
	}
	
		//삭제를 처리할 메서드
	@PostMapping("/board/remove")
	public String remove(Long bno , RedirectAttributes rttr) {
			log.info("삭제 처리"+bno);
			
			boardService.removeBoardandReply(bno);
			rttr.addFlashAttribute("msg"+bno+"삭제");
			
			return "redirect:/board/list";
	}
	
}
