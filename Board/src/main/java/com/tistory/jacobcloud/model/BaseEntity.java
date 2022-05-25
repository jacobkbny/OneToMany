package com.tistory.jacobcloud.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

//데이터베이스 작업을 감시하도록 role를줌
//Listener : 이벤트가 발생했을 떄 처리하는 객체
//테이블로 생성하지 않는 Entity를 생성하기 위한 설정
@MappedSuperclass  //이 클래스는 슈퍼클래스(인터페이스처럼) 일뿐이니 나를 mapping하는데 스레드를 쓰지 말아라(자원 절약)
@EntityListeners(value= {AuditingEntityListener.class})
@Data
//abstract는 추상 클래스기 때문에 인스턴스를 만들수없음
public abstract class BaseEntity {
	
	@CreatedDate
	@Column(name="regdate",updatable=false)
	private LocalDateTime regdate;
	
	@LastModifiedDate
	@Column(name="moddate")
	private LocalDateTime moddate;
}