package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity//db가 인식하게 만든다 (해당클래스로 테이블을 만든다)
@AllArgsConstructor//생성자
@NoArgsConstructor//생성자에 변수가 없을때 값
@ToString
@Getter
public class Article {

    @Id//대표값지정
    @GeneratedValue(strategy = GenerationType.IDENTITY/*db가 자동으로 만들어줌)*/)//자동 생성
    private Long id;

    @Column//db가 인식하게
    private String title;

    @Column
        private String content;

        public void patch(Article article) {
            if (article.title != null)
                this.title = article.title;
            if (article.content != null)
                this.content = article.content;
    }
}
