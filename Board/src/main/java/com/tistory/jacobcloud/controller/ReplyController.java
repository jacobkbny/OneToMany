package com.tistory.jacobcloud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tistory.jacobcloud.dto.ReplyDTO;
import com.tistory.jacobcloud.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Log4j2
@RestController
@RequestMapping("/replies/")
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;
	@GetMapping(value="/board/{bno}" , produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){
		List<ReplyDTO> list = replyService.getList(bno);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("")
	//클라이언트에서 JSON형태로 보낸 문자열을 ReplyDTO로 변경해서 저장합니다
	public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
		log.info("ReplyDTO:"+replyDTO);
		Long rno = replyService.register(replyDTO);
		return new ResponseEntity<>(rno,HttpStatus.OK);
	}
	@DeleteMapping(value="/{rno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteReply(@PathVariable("rno") Long rno){
				replyService.remove(rno);
		return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
	}
	@PutMapping("/{rno}")
	public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {
	log.info(replyDTO);
	replyService.modify(replyDTO);
	return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	
}
