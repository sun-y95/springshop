package com.shop.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shop.dto.CommentDTO;
import com.shop.dto.QnaDTO;
import com.shop.service.CommentService;
import com.shop.service.QnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class QnaController {
	
	private final QnaService qnaService;
	private final CommentService commentService;
	
	


    @PostMapping("/qna")
    public String save(@PageableDefault(page = 1) Pageable pageable,@ModelAttribute QnaDTO qnaDTO, Model model) {

        qnaService.save(qnaDTO);
        log.info("QnaDTO:!!!!!!!!!!!!!!!!!!!! " + qnaDTO);

        findAll(pageable, model, qnaDTO);
        return "qnaindex";
    }

	@GetMapping("/qna")
    public String findAll(@PageableDefault(page = 1) Pageable pageable, Model model,@ModelAttribute QnaDTO qnaDTO) {
        
       // List<QnaDTO> qnaDTOList = qnaService.findAll();

       
    	Page<QnaDTO> qnaList = qnaService.paging(pageable);
    	
    	int blockLimit = 3; //페이지 번호 갯수의 변수값
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; //1 4 7 10~
		int endPage = ((startPage + blockLimit - 1) < qnaList.getTotalPages()) ? startPage + blockLimit - 1 : qnaList.getTotalPages();
		
		
		model.addAttribute("qnaList",qnaList);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);

        return "qnaindex";
    }
    
    @GetMapping("/qna/{id}")
    public String findbyId(@PathVariable Long id, Model model,
    		@PageableDefault(page=1) Pageable pageable) {
    	
    	qnaService.updateHits(id);
    	
    	
    	QnaDTO qnaDTO = qnaService.findById(id);
    	
    	List<CommentDTO> commentDTOList= commentService.findAll(id);
    	model.addAttribute("commentList",commentDTOList);
    	model.addAttribute("qna",qnaDTO);

    	
    	return "qnadetail";
    }
    
    @GetMapping("/qna/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
    	QnaDTO qnaDTO = qnaService.findById(id);
    	model.addAttribute("qnaUpdate",qnaDTO);
    	return "qnaupdate";
    }  
    
    @PostMapping("/qna/update")
    public String update(@ModelAttribute QnaDTO qnaDTO, Model model) {
    	QnaDTO qna = qnaService.update(qnaDTO);
    	model.addAttribute("qna",qna);
    	return "qnadetail";
    }
    
    @GetMapping("/qna/delete/{id}")
    public String delete(@PathVariable Long id) {
     	commentService.deleteByQnaId(id);
    	qnaService.delete(id);

    	return "redirect:/qna";
    	
    }
    
 
    
    
}