package com.sparta.post.dto;

import lombok.Getter;

@Getter         //Dto -> Date transfer object -> 데이터 전달 객체 -> 데이터의 통로
public class PostRequestDto {
    private String title;
    private String content;
    private String author;
    private String password;

}
