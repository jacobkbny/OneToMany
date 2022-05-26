package com.tistory.jacobcloud.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	private Long bno;
	private String title;
	private String content;
	private LocalDateTime regdate;
	private LocalDateTime moddate;
	
	
	private String memberEmail;
	private String memberName;
	
	private int replyCount;
	
}
