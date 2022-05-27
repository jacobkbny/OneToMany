package com.tistory.jacobcloud.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.tistory.jacobcloud.model.Board;
import com.tistory.jacobcloud.model.QBoard;
import com.tistory.jacobcloud.model.QMember;
import com.tistory.jacobcloud.model.QReply;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

	public SearchBoardRepositoryImpl() {
		super(Board.class);
		
		
		
	}

	@Override
	public Board search() {
			log.info("search");
			/*
				QBoard board = QBoard.board;
			JPQLQuery<Board> jpqlQeury = from(board);
				jpqlQeury.select(board).where(board.bno.eq(50L));
				List<Board> result = jpqlQeury.fetch();
				log.info("결과입니다"+result);
			 * */
			//join의 경우
			//쿼리를 수행할 수 있는 Querydsl 객체를 찾아옴.(테이블들을 다 가져옴) 
			QBoard board = QBoard.board;
			QReply reply = QReply.reply;
			QMember member = QMember.member;
			
			//관계에서 부모에 해당하는 Entity를 기준으로 JPQLQuery를 생성
			JPQLQuery<Board> jpql = from(board);
			
			jpql.leftJoin(reply).on(reply.board.eq(board));
			jpql.leftJoin(member).on(board.member.eq(member));
			JPQLQuery<Tuple> tuple = jpql.select(board,member.email,reply.count());
			
			
				
				
					
				//필요한 데이터를 조회하는 쿼리문
				//보드 전체 다 , 그리고 멤버에서 email , 그리고 reply 카운트 그룹바이 보드로 해서 가져오기
			jpql.select(board,member.email,reply.count()).groupBy(board);
			// 이 쿼리문 한 문장으로 보드 테이블이 에있는 전부 , 멤버 테이블의 작성자 , 그리고 reply테이블에서 그 bno에 해당하는 게시글의 reply count 다 나옴.
				// 근데 결과 값을 Board Entity형태로 받기 떄문에 Board Entity에 종속 될수밖에 없음 (단점) 
				//프로그래밍에서는 일반적으로 여러 종류의 데이터가 묶여서 하나의 데이터를 나타내는 자료형
				//map과 다른점은 Map은 Key로 세부 데이트를 접근해야 하지만 Tuple은 인덱스로도 접근이 가능하고,
				//대부분의 경우 Tuple은 수정이 불가능 
			List<Board> result = jpql.fetch();
				log.info("서치보드 리포지토리에서 쿼리문 실행후 결과입니다"+result);
		return null;
	}

	@Override
	public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;
		QMember member = QMember.member;
		
		JPQLQuery<Board> jpql = from(board);
		jpql.leftJoin(member).on(board.member.eq(member));
		jpql.leftJoin(reply).on(reply.board.eq(board));
			//검색 결과를 만들어주는 부분 
		JPQLQuery<Tuple> tuple = jpql.select(board,member,reply.count());
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression expression = board.bno.gt(0);
		booleanBuilder.and(expression);

			if(type != null) {
				String [] arr = type.split("");
				BooleanBuilder conditionBuilder = new BooleanBuilder();
				for(String a : arr) {
						switch(a) {
							case "t":
								conditionBuilder.or(board.title.contains(keyword));
								break;
							case "C":
								conditionBuilder.or(board.content.contains(keyword));
								break;
							case "W":
								conditionBuilder.or(member.email.contains(keyword));
								break;
						}
					}
					booleanBuilder.and(conditionBuilder);
			}
					tuple.where(booleanBuilder);
						
						
						Sort sort = pageable.getSort();
						sort.stream().forEach(order ->{
							Order Direction = order.isAscending()?Order.ASC :Order.DESC;
							String prop = order.getProperty();
							
							PathBuilder orderByExpression = new PathBuilder(Board.class , "board");
							tuple.orderBy(new OrderSpecifier(Direction , orderByExpression.get(prop)));
						});
						
						tuple.groupBy(board);
						tuple.offset(pageable.getOffset());
						tuple.limit(pageable.getPageSize());
						
						List<Tuple> result = tuple.fetch();
						log.info("서치보드레파지토리임플"+result);
						
						long count = tuple.fetchCount();
						
						
							return new  PageImpl<Object[]>(
										result.stream().map(t->t.toArray()).collect(Collectors.toList()),
										pageable,count);
									
	}

}
