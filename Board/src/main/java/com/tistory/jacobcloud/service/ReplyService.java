package com.tistory.jacobcloud.service;

import java.util.List;

import com.tistory.jacobcloud.dto.ReplyDTO;
import com.tistory.jacobcloud.model.Board;
import com.tistory.jacobcloud.model.Reply;

public interface ReplyService {
	
	//댓글 등록
	public Long register(ReplyDTO replyDTO);
	//게시글 번호를 이용해서 댓글의 목록을 가져오는 메서드
	public List<ReplyDTO> getList(Long bno);
	//댓글을 수정하는 메서드
	public void modify (ReplyDTO replyDTO);
	//댓글을 삭제하는 메서드
	public void remove(Long rno);
	
	default Reply dtoToEntity(ReplyDTO replyDTO) {
		Board board = Board.builder().bno(replyDTO.getBno()).build();
		Reply reply = Reply.builder().rno(replyDTO.getRno())
				.text(replyDTO.getText())
				.replyer(replyDTO.getReplyer())
				.board(board)
				.build();
		
			return reply;
	}
	
	//replyEntity를 replyDTO로 변환
	
	default ReplyDTO entityToDto(Reply reply) {
		ReplyDTO dto = ReplyDTO.builder().rno(reply.getRno())
				.text(reply.getText())
				.replyer(reply.getReplyer())
				.regdate(reply.getRegdate())
				.moddate(reply.getModdate())
				.build();
			return dto;
	}
	

}
