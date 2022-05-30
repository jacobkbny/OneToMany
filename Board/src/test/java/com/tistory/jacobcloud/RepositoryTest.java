package com.tistory.jacobcloud;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.tistory.jacobcloud.model.Board;
import com.tistory.jacobcloud.model.Member;
import com.tistory.jacobcloud.model.Reply;
import com.tistory.jacobcloud.persistence.BoardRepository;
import com.tistory.jacobcloud.persistence.MemberRepository;
import com.tistory.jacobcloud.persistence.ReplyRepository;
import com.tistory.jacobcloud.persistence.SearchBoardRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class RepositoryTest {

		@Autowired
		private MemberRepository memberRepository;
		
		
		
//		@Test
		public void inserMember() {
			IntStream.rangeClosed(1, 100).forEach(i->{
				Member member = Member.builder().email("pkb"+i+"@gmail.com").password("1111").name("jac"+i).build();
				memberRepository.save(member);
			});
		}
		@Autowired 
		private BoardRepository boardRepository;
//		@Test
		public void inserBoard() {
			IntStream.rangeClosed(1, 100).forEach(i->{
				Member member = Member.builder().email("pkb74@gmail.com").build();
				Board board = Board.builder().title("제목.."+i).content("내용.."+i).member(member).build();
				boardRepository.save(board);
			});
		}
		
		@Autowired
		private ReplyRepository replyRepository;
//		@Test
		public void inserReply() {
			IntStream.rangeClosed(1, 100).forEach(i->{
				Member member = Member.builder().email("pkb74@gmail.com").build();
					Random r = new Random();
						long bno ;
						long ran_num = r.nextLong(100);
						if(ran_num>5) {
							bno = ran_num;	
						}else {
								bno=ran_num+5;
						}
								
							System.out.println("보드넘버"+bno);
						Board board = Board.builder().bno(bno).build();
				Reply reply = Reply.builder().text("댓글입니다"+i).board(board).build();
				replyRepository.save(reply);
			});
		}
		
		//하나의 Board 데이터를 조회하는 메서드
			@Transactional
//			@Test
		public void getBoard() {
				Random r = new Random();
					long bno = r.nextLong(100)+5;
						Optional<Board>result=boardRepository.findById(bno);
						System.out.println(result);
							
			
		}
//		@Test
		public void readReply() {
			 	Random r = new Random();
			 		Long rno = r.nextLong(521)+406;
			 		Optional<Reply>result = replyRepository.findById(rno);
			 		System.out.println("reply result"+result);
		}
//			@Test
		public void testReadWithWriter() {
			Object result = boardRepository.getBoardWithMember(100L);
				//JQL의 결과가 Object인 경우는 Object[]로 강제 형 변환해서 사용 
				System.out.println(Arrays.toString((Object[])result));
		}
//			@Test
			public void testGetBoardWithReply() {
					List<Object[]>result = boardRepository.getBoardWithReply(98L);
						for(Object[] ar : result) {
								System.out.println(Arrays.toString(ar));
						}
			}
		
//		@Test	
		public void testWithReplyCount() {
				Pageable pageable = PageRequest.of(0, 10,Sort.by("bno").descending());
				Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
				
				result.get().forEach(row ->{
					Object[] ar = (Object[])row;
						System.out.println(Arrays.toString(ar));
				});
		}
//		@Test
		public void testByBno() {
			Object result = boardRepository.getBoardByBno(100L);
				Object[] ar = (Object[])result;
					System.out.println(Arrays.toString(ar));
			
		}
		
//		@Test
		public void testSearch() {
			boardRepository.search();
				
				
				
			
		}
//			@Autowired
//			private SearchBoardRepository searchBoardRepository;
////		@Test
//		public void testSearchPage() {
//			Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending().and(Sort.by("title").ascending()));
//			Page<Object[]> result = searchBoardRepository.searchPage("t", "1", pageable);
//				log.info("테스트 서치 페이지"+result);
//		}
		
		@Test
		public void testListByBoard(){
			List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(30L).build());
				System.out.println(replyList);
		}
		
}
