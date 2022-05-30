package com.tistory.jacobcloud.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tistory.jacobcloud.dto.ReplyDTO;
import com.tistory.jacobcloud.model.Board;
import com.tistory.jacobcloud.model.Reply;
import com.tistory.jacobcloud.persistence.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	private final ReplyRepository replyRepository;
	@Override
	public Long register(ReplyDTO replyDTO) {
		Reply reply = dtoToEntity(replyDTO);
			replyRepository.save(reply);
			return reply.getRno();
	}

	@Override
	public List<ReplyDTO> getList(Long bno) {
			Board board = Board.builder().bno(bno).build();
		List<Reply> result = 
					replyRepository.getRepliesByBoardOrderByRno(board);
			result.sort(new Comparator<Reply>() {

				@Override
				public int compare(Reply o1, Reply o2) {
						//양수를 리턴하면 내림차순
						//음수를 리턴하면 오름차순 
						//수정 시간의 내림차순
						//오름차순의 경우는 o1과 o2 의 순서를 변경하면 됨.
						// 숫자의 경우 양수가 리턴되면 앞의(o1) 데이터가 크다고 판단하고
						// 음수가 리턴되면 o2가 더 크다고 판단해서 
						// 음수가 리턴될 떄 순서가 변경.
						// 자바스크립트에서 데이터를 정렬할 떄 주의해야함.
						// 자바 스크릅트는 숫자도 문자열로 판단해서 정렬 
						// 숫자 데이터의 경우 정렬하고자 할 떄 직접 구현해야함.
						// [200,90] 이렇게 있으면 js는 90 더 크다고 생각 
					return o2.getModdate().compareTo(o1.getModdate());
				}
			});
		return result.stream().map(reply->entityToDto(reply)).collect(Collectors.toList());
	}

	@Override
	public void modify(ReplyDTO replyDTO) {
		Reply reply = dtoToEntity(replyDTO);
			replyRepository.save(reply);
	}

	@Override
	public void remove(Long rno) {
			replyRepository.deleteById(rno);
	}

}
