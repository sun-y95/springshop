package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.shop.dto.QnaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Qna extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String qnaTitle;
	
	@Column(length = 20, nullable = false) //크기 20, not null
	private String qnaWriter;
	
	@Column
	private String qnaPass;
	
	@Column
	private String qnaContent;
	
	@Column
	private int qnaHits;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	
	
	public static Qna toSaveEntity(QnaDTO qnaDTO) {
		Qna qna = new Qna();
		qna.setQnaTitle(qnaDTO.getQnaTitle());
		qna.setQnaContent(qnaDTO.getQnaContent());
		qna.setQnaWriter(qnaDTO.getQnaWriter());
		qna.setQnaPass(qnaDTO.getQnaPass());
		qna.setQnaHits(0);
		return qna;
	}
	
	public static Qna toUpdateEntity(QnaDTO qnaDTO) {
		Qna qna = new Qna();
		qna.setId(qnaDTO.getId());
		qna.setQnaWriter(qnaDTO.getQnaWriter());
		qna.setQnaPass(qnaDTO.getQnaPass());
		qna.setQnaTitle(qnaDTO.getQnaTitle());
		qna.setQnaContent(qnaDTO.getQnaContent());
		qna.setQnaHits(0);
		return qna;
	}


	


}
