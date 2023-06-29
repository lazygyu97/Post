package com.sparta.post.controller;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.*;


//강의 1장 내용을 바탕으로 코드를 구현함. --> 강의 2장 내용을 바탕으로 jpa 적용
//mysql과 같은 외부 데이터베이스를 이용하지 않음. --> mysql 사용함
//과제 설명에 나와있는 API명세와 요구 사항을 기반으로 작성.

/*
개인 과제 요구사항 체크

1. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기  -- 성공
    - 작성 날짜 기준 내림차순으로 정렬하기    -- 실패 -> Auditing 기능을 활용 하여 성공
2. 게시글 작성 API
    - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고 -- 성공
    - 저장된 게시글을 Client 로 반환하기 -- 성공
3. 선택한 게시글 조회 API
    - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 -- 성공
4. 선택한 게시글 수정 API
    - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후-- 성공
    - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기-- 성공
5. 선택한 게시글 삭제 API
    - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후-- 성공
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기-- 성공

필수 요구사항은 아니지만 추가 해본 기능
1. 키워드를 통한 글 검색 API
    - 입력받은 키워드가 포함된 글들 전부 가져오기 -- 성공
 */

@RestController //--> 컨트롤러
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    //게시물 생성 로직
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    //게시글 전체 조회
    //작성 날짜 기준 내림차순으로 정렬하기를 구현하지 못함. --> Auditing 기능으로 구현함
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    // 선택한 게시글 보기
    @GetMapping("/post/{id}")
    public PostResponseDto detailPost(@PathVariable Long id) {
        return postService.detailPost(id);
    }

    //비밀번호 검증을 통한 게시글 수정
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    //비밀번호 검증을 통한 게시글 삭제
    @DeleteMapping("/post/{id}")
    public boolean deletePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.deletePost(id, requestDto);
    }

    //키워드를 통한 글 검색
    @GetMapping("/post/keyword/{keyword}")
    public List<PostResponseDto> findPost(@PathVariable String keyword){
        return postService.findPost(keyword);
    }

}
