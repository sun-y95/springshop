package com.shop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.CommentDTO;
import com.shop.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	
	@PostMapping("/qna/comment")
	public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
		
		Long saveResult = commentService.save(commentDTO);
		if(saveResult != null) {
			
			List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getQnaId());
			return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
		}
		return new ResponseEntity<>("해당 게시글이 존재하지 않습니다." , HttpStatus.NOT_FOUND);
	}
}
