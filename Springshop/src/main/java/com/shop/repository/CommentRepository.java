package com.shop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Comment;
import com.shop.entity.Qna;

public interface CommentRepository extends JpaRepository<Comment, Long>{
		List<Comment> findAllByQnaEntityOrderByIdDesc(Qna qna);
	
	    @Transactional
	    @Modifying
	    @Query("DELETE FROM Comment c WHERE c.qnaEntity.id = :id")
	    void deleteByQnaId(@Param("id") Long id);
}
