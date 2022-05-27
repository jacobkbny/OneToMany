package com.tistory.jacobcloud.service;

import com.tistory.jacobcloud.dto.BoardDTO;
import com.tistory.jacobcloud.dto.PageRequestDTO;
import com.tistory.jacobcloud.dto.PageResultDTO;
import com.tistory.jacobcloud.model.Board;
import com.tistory.jacobcloud.model.Member;

public interface BoardService {
		//게시물 등록을 위한 메서드
		Long register(BoardDTO dto);
		//목록보기 메서드
		//PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
		PageResultDTO <BoardDTO,Object[]>getList(PageRequestDTO pageRequestDTO);
		//상세보기 메서드
		// 상세보기를 하려면 bno를 주고 모든걸 가져오라고 해야함 근데 모든것을 받으려면 object 타입으로 받아야함
		 BoardDTO getBoard(Long bno);
		 //게시글 삭제 메서드
		 void removeBoardandReply(Long bno);
		 void modifyBoard(BoardDTO boardDTO);
		 
		//DTO를 Entity로 변환해주는 매서드
		default Board dtoToEntity(BoardDTO dto) {
			Member member = Member.builder().email(dto.getMemberEmail()).build();
			
			Board board = Board.builder().bno(dto.getBno()).title(dto.getTitle()).content(dto.getContent()).member(member).build();
					return board;
		}
		
		//Entity를 DTO로 변환
		
		default BoardDTO entityToDTO(Board board, Member member , Long replyCount) {
				BoardDTO dto = BoardDTO.builder().bno(board.getBno())
								.title(board.getTitle())
								.content(board.getContent())
								.regdate(board.getRegdate())
								.moddate(board.getModdate())
								.memberEmail(member.getEmail())
								.memberName(member.getName())
								.replyCount(replyCount.intValue())
								.build(); 
								return dto;
				}
}
