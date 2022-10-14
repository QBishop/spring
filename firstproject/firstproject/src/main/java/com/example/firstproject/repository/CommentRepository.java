package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //특정 게시글의 모든 댓글 조회
    @Query(value =
            "SELECT * " +
                    "FROM comment " +
                    "WHERE article_id = :articleId",
            nativeQuery = true)//해당 SQL문을 사용할수 있도록 함
    List<Comment> findByArticleId(@Param("articleId") Long articleId);//articleId위꺼랑 같아야함



    //특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(@Param("nickname")String Nickname);//해당 수행할 수 있는 SQL을 XML로 작성했다.
}
