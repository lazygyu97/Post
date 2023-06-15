package com.sparta.post.dto;

import lombok.Getter;


@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private String author;
    private String password;
    private String createdAt; // 글 생성 시간
    private String modifiedAt; // 글 수정 시간
}
