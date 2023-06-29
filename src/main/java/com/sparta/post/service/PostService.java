package com.sparta.post.service;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.entity.Post;
import com.sparta.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) { //가독성

        Post post = new Post(postRequestDto);

        Post savePost = postRepository.save(post);

        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto detailPost(Long id) {
        Post post = checkPost(id);
        post = postRepository.findById(id).get();
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = checkPost(id);
        if (Objects.equals(post.getPassword(), requestDto.getPassword())) {
            post.update(requestDto);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return postResponseDto;
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public boolean deletePost(Long id, PostRequestDto requestDto) {
        Post post = checkPost(id);
        if (Objects.equals(post.getPassword(), requestDto.getPassword())) {
            postRepository.delete(post);
            return true;
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public List<PostResponseDto> findPost(String keyword) {
        if (!postRepository.findAllByContentContainsOrderByModifiedAtDesc(keyword).isEmpty()) {
            return postRepository.findAllByContentContainsOrderByModifiedAtDesc(keyword).stream().map(PostResponseDto::new).toList();
        } else {
            throw new IllegalArgumentException(keyword + "가 포함되는 게시글이 없습니다");
        }
    }

    private Post checkPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시물은 존재하지 않습니다.")
        );
    }


}
