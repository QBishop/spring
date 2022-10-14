package com.example.firstproject.dto.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // 로깅을 해준다.
public class ArticleController {

    @Autowired//스프링 부트가 미리 생성한 객체를 가져와 자동 연결
    private ArticleRepository articleRepository;//new를 이용한 객체를 안만들어도됨 스프링이 알아서함

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString());

        //1.dto를 변환! entity
        Article article = form.toEntity();//entity로 바꿔주는 메소드
        log.info(article.toString());
        //System.out.println(article.toString());

        //2.repository가 entity를 db안에 저장시키도록 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());

        return "redirect:/articles/"+saved.getId();//해당 글을 작성했을때 id를 가져오고 그값을 리다이렉트한다.
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable/*url에서 받는다.*/ Long id, Model model) {
        log.info("id = "+id);
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        model.addAttribute("article",articleEntity);//article이라는 이름으로 좀전에 받은 데이터를 모델에 넣어준다.
        model.addAttribute("commentDtos",commentDtos);

        return "articles/show";//새로운 html(mustache)를 준다.
    }

    @GetMapping("/articles")
    public String index(Model model) {
        List<Article> articleEntityList = articleRepository.findAll();

        model.addAttribute("articleList",articleEntityList);

        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id,Model model) {

        Article articleEntity = articleRepository.findById(id).orElse(null);

        model.addAttribute("article",articleEntity);
        return "articles/edit";//수정 페이지를 보내준다.
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        Article articleEntity = form.toEntity();//폼을 엔티티로 변환

        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        if(target!=null) {
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {

        Article target =articleRepository.findById(id).orElse(null);

        if(target != null) {
            articleRepository.delete(target);
        }
        rttr.addFlashAttribute("msg","삭제가 완료됬습니다!");

        return "redirect:/articles";
    }
}
