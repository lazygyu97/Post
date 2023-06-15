package com.sparta.post.dto;

import com.sparta.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdAt; // 글 생성 시간
    private String modifiedAt; // 글 수정 시간


    public PostResponseDto(Post post) {
        this.id= post.getId();
        this.title= post.getTitle();
        this.content= post.getContent();
        this.author= post.getAuthor();
        this.createdAt= post.getCreatedAt();
        this.modifiedAt= post.getModifiedAt();
    }

}
