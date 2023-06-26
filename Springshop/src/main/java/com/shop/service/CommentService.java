package com.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shop.dto.CommentDTO;
import com.shop.entity.Comment;
import com.shop.entity.Qna;
import com.shop.repository.CommentRepository;
import com.shop.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final QnaRepository qnaRepository;

	public Long save(CommentDTO commentDTO) {
		Optional<Qna> optionalQna = qnaRepository.findById(commentDTO.getQnaId());
		if(optionalQna.isPresent()) {
			Qna qnaEntity = optionalQna.get();
			Comment qnaComment = Comment.toSaveEntity(commentDTO,qnaEntity);
			return commentRepository.save(qnaComment).getId();
		}else {
			return null;
		}
	}
	
	
	@Transactional
	public List<CommentDTO> findAll(Long qnaId) {
		Qna qnaEntity = qnaRepository.findById(qnaId).get();
		List<Comment> commentEntityList = commentRepository.findAllByQnaEntityOrderByIdDesc(qnaEntity);
		
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for(Comment commentEntity: commentEntityList) {
			CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity,qnaId);
			commentDTOList.add(commentDTO);
		}
		return commentDTOList;
	}

	
	@Transactional
	public void deleteByQnaId(Long id) {
		commentRepository.deleteByQnaId(id);
	}


}
