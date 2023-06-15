package com.sparta.post.controller;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.entity.Post;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//개인 과제 요구사항 체크
//강의 1장 내용을 바탕으로 코드를 구현함.
//mysql과 같은 외부 데이터베이스를 이용하지 않음.
//과제 설명에 나와있는 API명세와 요구 사항을 기반으로 작성.

/*
1. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기  -- 성공
    - 작성 날짜 기준 내림차순으로 정렬하기    -- 실패
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
 */

@RestController
@RequestMapping("/api")
public class PostController {

    //Sql을 사용하지 않기 때문에 데이터를 담아줄 공간으로서 해쉬맵을 선언해 준다.
    private final Map<Long, Post> postList = new HashMap<>();

    private String time() {
        LocalDateTime now = LocalDateTime.now();//현재 시간 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");//시간 형식 정하기
        String formattedDateTime = now.format(formatter);//위의 두 코드 합쳐주기
        return formattedDateTime;
    }


    //게시물 생성 로직
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {

        // RequestDto -> Entity
        Post post = new Post(requestDto, time());

        // Post Max ID Check
        Long maxId = postList.size() > 0 ? Collections.max(postList.keySet()) + 1 : 1;
        post.setId(maxId);

        // DB 저장-> postList에 저장
        postList.put(post.getId(), post);

        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }

    //게시글 전체 조회
    //작성 날짜 기준 내림차순으로 정렬하기를 구현하지 못함.
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        // Map To List
        List<PostResponseDto> responseList = postList.values().stream()
                .map(PostResponseDto::new).toList();

        return responseList;
    }

    // 선택한 게시글 보기
    @GetMapping("/post/{id}")
    public PostResponseDto detailPost(@PathVariable Long id) {
        // 해당 게시글이 DB에 존재하는지 확인
        if (postList.containsKey(id)) {
            // 해당 게시글 가져오기
            Post post = postList.get(id);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return postResponseDto;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }


    //비밀번호 검증을 통한 게시글 수정
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        // 해당 게시글이 DB에 존재하는지 확인
        if (postList.containsKey(id)) {
            //requestDto에 담긴 password (입력 받은 비밀번호)와 데이터베이스의 값이 같은지 확인
            if (postList.get(id).getPassword().equals(requestDto.getPassword())) {
                // 해당 게시글 가져오기
                Post post = postList.get(id);
                // 게시글 수정
                post.update(requestDto, time());
                PostResponseDto postResponseDto = new PostResponseDto(post);
                return postResponseDto;
            }else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }


    //비밀번호 검증을 통한 게시글 삭제
    @DeleteMapping("/post/{id}")
    public boolean deletePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        // 해당 게시글 DB에 존재하는지 확인
        if (postList.containsKey(id)) {
            //requestDto에 담긴 password (입력 받은 비밀번호)와 데이터베이스의 값이 같은지 확인
            if (postList.get(id).getPassword().equals(requestDto.getPassword())) {
                // 해당 게시글 삭제하기
                postList.remove(id);
                return true;
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }

}
