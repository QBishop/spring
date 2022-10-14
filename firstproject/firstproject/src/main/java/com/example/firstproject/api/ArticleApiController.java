package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired //외부에서 가져온다.
    private ArticleService articleService;

    //get
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    //get 1개만
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    //post
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody/*json형식으로 받을때는 body내용만 받아줘야한다.*/ ArticleForm dto/*데이터를 받아올때 dto로 받아준다*/) {
        Article created = articleService.create(dto);//dto를 엔티티로 바꿔준다.
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //patch
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id,dto);
        return (updated!=null) ? ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //트랜잭션 > 실패 > 롤백
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article>createdList = articleService.createArticles(dtos);
        return (createdList != null) ?
               ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }









}
