package com.tistory.jacobcloud.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@ToString
public class Reply extends BaseEntity {
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long rno;
		private String text;
		private String replyer;
	
}
