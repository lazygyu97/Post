package com.sparta.post.entity;

import com.sparta.post.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 생성
public class Post {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String password;
    private String createdAt; // 글 생성 시간
    private String modifiedAt; // 글 수정 시간

    //게시글 추가 생성자
    public Post(PostRequestDto requestDto,String createdAt) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.content = requestDto.getContent();
        this.password= requestDto.getPassword();
        this.createdAt=createdAt;
    }

    //게시글 수정 생성자
    public void update(PostRequestDto requestDto,String modifiedAt) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.content = requestDto.getContent();
        this.password= requestDto.getPassword();
        this.modifiedAt=modifiedAt;
    }
}
