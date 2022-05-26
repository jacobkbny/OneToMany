package com.tistory.jacobcloud.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;




@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude="member")
public class Board extends BaseEntity {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long bno;
	private String title;
	private String content;
	
	public void changeTitle(String title) {
			this.title = title;
			
	}
	public void changeContent(String content) {
		this.content = content;
	}
	
		//필요할때 꺼내와라 미리 서버 로딩될떄 꺼내오지말고 lazy
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member ;
}
