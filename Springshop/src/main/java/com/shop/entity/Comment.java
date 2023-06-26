package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shop.dto.CommentDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class Comment extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=20, nullable = false)
	private String commentWriter;
	
	@Column
	private String commentContents;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "qna_id")
	 private Qna qnaEntity;


	public static Comment toSaveEntity(CommentDTO commentDTO, Qna qnaEntity) {
		Comment commentEntity = new Comment();
		commentEntity.setCommentWriter(commentDTO.getCommentWriter());
		commentEntity.setCommentContents(commentDTO.getCommentContents());
		commentEntity.setQnaEntity(qnaEntity);
		
		return commentEntity;
		
	}
	
	
}
