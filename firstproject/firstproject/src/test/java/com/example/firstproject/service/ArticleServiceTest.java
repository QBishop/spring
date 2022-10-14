package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest//해당 클래스는 스프링부트와 연동되어 테스트 된다.
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    @Test
    void index() {
        //예상
        Article a = new Article(1L,"가가가","1111");
        Article b = new Article(2L,"나나나","2222");
        Article c = new Article(3L,"다다다","3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));
        //실제
        List<Article> articles= articleService.index();
        //비교
        assertEquals(expected.toString(),articles.toString());

    }

    @Test
    void show_성공_존재하는id_입력() {
        //예상
        Long id = 1L;
        Article expected = new Article(id,"가가가","1111");
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    void show_실패_존재하지않는_id() {
        //예상
        Long id = -1L;
        Article expected = null;
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected,article);//toString은 null을 못받는다.
    }

    @Test
    @Transactional
    void create_성공_title과content만있는_dto입력() {
        //예상
        String title = "라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null,title,content);
        Article expected = new Article(4L,title,content);
        //실제
        Article article = articleService.create(dto);
        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    @Transactional//테스트가 끝나면 데이터를 롤백한다.
    void create_실패_id가포함된dto입력() {
        //예상
        String title = "라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L,title,content);
        Article expected = null;
        //실제
        Article article = articleService.create(dto);
        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void update_성공_존재하는_id_title_content가있는dto입력() {
        //예상
        Long id = 1L;
        String title = "바바바";
        String content = "8888";
        ArticleForm dto = new ArticleForm(1L,title,content);
        Article expected = new Article(id,"바바바","8888");
        //실제
        Article article = articleService.update(id,dto);
        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    @Transactional//테스트가 끝나면 데이터를 롤백한다.
    void update_성공_존재하는_id_title만있는있는dto입력() {
        //예상
        Long id = 1L;
        String title = "바바바";
        ArticleForm dto = new ArticleForm(1L,title,null);
        Article expected = new Article(id,"바바바","1111");
        //실제
        Article article = articleService.update(id,dto);
        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    @Transactional
    void update_실패_존재하지_않는id의dto입력() {
        //예상
        Long id = -1L;
        String title = "바바바";
        ArticleForm dto = new ArticleForm(-1L,title,null);
        Article expected = null;
        //실제
        Article article = articleService.update(id,dto);
        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void update_실패_id만있는dto입력() {
        //예상
        Long id = -1L;
        String title = "바바바";
        ArticleForm dto = new ArticleForm(-1L,title,null);
        Article expected = null;
        //실제
        Article article = articleService.update(id,dto);
        //비교
        assertEquals(expected,article);

    }

    @Test
    @Transactional
    void delete_성공_존재하는id입력() {
        //예상
        Long id = 1L;
        Article expected = null;
        //실제
        articleService.delete(id);
        Article article = articleService.show(id);
        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void update_실패_존재하지않는id입력() {
        //예상
        Long id = -1L;
        Article expected = null;
        //실제
        Article article = articleService.delete(id);
        //비교
        assertEquals(expected,article);

    }
}