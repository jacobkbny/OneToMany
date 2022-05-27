package com.tistory.jacobcloud.service;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.tistory.jacobcloud.dto.BoardDTO;
import com.tistory.jacobcloud.dto.PageRequestDTO;
import com.tistory.jacobcloud.dto.PageResultDTO;
import com.tistory.jacobcloud.model.Board;
import com.tistory.jacobcloud.model.Member;
import com.tistory.jacobcloud.persistence.BoardRepository;
import com.tistory.jacobcloud.persistence.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
		private final BoardRepository boardRepository;
	@Override
	public Long register(BoardDTO dto) {
			log.info(dto);
			Board board=	dtoToEntity(dto);
		boardRepository.save(board);
		return board.getBno();
	}
	@Override
	public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
			log.info(pageRequestDTO);
			Function<Object[],BoardDTO> fn = (en ->
				entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));
			
//			Page<Object[]> result = boardRepository.getBoardWithReplyCount(
//						pageRequestDTO.getPageable(Sort.by("bno").descending()));
			Page<Object[]> result = boardRepository.searchPage(pageRequestDTO.getType(),
					pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("bno").descending()));
			return new PageResultDTO(result ,fn);
			}
	@Override
	public BoardDTO getBoard(Long bno) {
			Object result = boardRepository.getBoardByBno(bno);
			Object [] ar = (Object[]) result;
			
		
		return entityToDTO((Board)ar[0],(Member)ar[1], (Long)ar[2]);
	}
		private final ReplyRepository replyRepository;
	@Transactional
	@Override
	public void removeBoardandReply(Long bno) {
			//댓글 삭제 (reply쪽에서 삭제해야함)
		replyRepository.DeleteByBno(bno);
			//게시글 삭제
		boardRepository.deleteById(bno);
	}
	@Transactional
	@Modifying
	@Override
	public void modifyBoard(BoardDTO boardDTO) {
			//데이터의 존재 여부를 확인
		Optional<Board> board = boardRepository.findById(boardDTO.getBno());
			if(board.isPresent()) {
				board.get().changeTitle(boardDTO.getTitle());
				board.get().changeContent(boardDTO.getContent());
					boardRepository.save(board.get());
			}				
	}
	
		}

