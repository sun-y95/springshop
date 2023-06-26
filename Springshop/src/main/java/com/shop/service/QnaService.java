package com.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shop.dto.QnaDTO;
import com.shop.entity.Member;
import com.shop.entity.Qna;
import com.shop.repository.MemberRepository;
import com.shop.repository.QnaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class QnaService {
	
	private final QnaRepository qnaRepository;
	private final MemberRepository memberRepository;
	
	
	public void save(QnaDTO qnaDTO) {
		
//		Optional<Member> memberOptional = memberRepository.findById(qnaDTO.getMemberId());
//		
//		if (memberOptional.isPresent()) {
//			Member member = memberOptional.get();
//			//Qna qna = Qna.toSaveEntity(qnaDTO, member);
//			
//			
//		}
		
		Qna qna = new Qna();
		qna.setQnaTitle(qnaDTO.getQnaTitle());
		qna.setQnaWriter(qnaDTO.getQnaWriter());
		qna.setQnaPass(qnaDTO.getQnaPass());
		qna.setQnaContent(qnaDTO.getQnaContent());
		qna.setModDate(qnaDTO.getModDate());
		qna.setRegDate(qnaDTO.getRegDate());
		qna.setQnaHits(0);
		qnaRepository.save(qna);
		
		System.out.println(qna+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

	}
	
	
	@Autowired
	public List<QnaDTO> findAll() {
		 log.info("findAll method called!!!!"); // 로깅 추가
		
		List<Qna> qnaEntityList = qnaRepository.findAll();
		List<QnaDTO> qnaDtoList = new ArrayList<>();
		
		for(Qna QnaEntity : qnaEntityList) {
			qnaDtoList.add(QnaDTO.toQnaDTO(QnaEntity));
		}
		return qnaDtoList;
		

	}

	
	@Transactional
	public void updateHits(Long id) {

		qnaRepository.updateHits(id);
	}

	@Transactional
	public QnaDTO findById(Long id) {
		Optional<Qna> optionalQna = qnaRepository.findById(id);
		if(optionalQna.isPresent()) {
			Qna qna = optionalQna.get();
			QnaDTO qnaDTO = QnaDTO.toQnaDTO(qna);
			return qnaDTO;
		}else {
			return null;
		}
	}


	public QnaDTO update(QnaDTO qnaDTO) {
		
		Qna qna = Qna.toUpdateEntity(qnaDTO);
		qnaRepository.save(qna);
		return findById(qnaDTO.getId());
		
	}


	public void delete(Long id) {
		qnaRepository.deleteById(id);
		
	}


	public Page<QnaDTO> paging(Pageable pageable) {
		
		int page = pageable.getPageNumber()-1;
		int pageLimit = 3;
		
		Page<Qna> qnaEntity = 
				qnaRepository.findAll(PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
		//System.out.println("qna.getContent()" + qnaEntity.getContent());
		
		Page<QnaDTO> qnaDTOS = qnaEntity.map(qna -> new QnaDTO(
				qna.getId(),
				qna.getQnaWriter(),
				qna.getModDate(),
				qna.getQnaContent(),
				qna.getQnaHits(),
				qna.getQnaTitle(),
				qna.getRegDate()));
		return qnaDTOS;
	}
	
	
	
	
	
}
