package com.shop.dto;

import java.time.LocalDateTime;

import com.shop.entity.Comment;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ToString
@Getter
@Setter
public class CommentDTO {
	private Long id;
	private String commentWriter;
	private String commentContents;
	private Long qnaId;
	private LocalDateTime commentCreatedTime;
	
	
	
	public static CommentDTO toCommentDTO(Comment commentEntity,Long qnaId) {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setId(commentEntity.getId());
		commentDTO.setCommentContents(commentEntity.getCommentContents());
		commentDTO.setCommentWriter(commentEntity.getCommentWriter());
		commentDTO.setQnaId(qnaId);
		commentDTO.setCommentCreatedTime(commentEntity.getRegDate());
		
		return commentDTO;
		
	}
}
