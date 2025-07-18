package com.koreait.service_impl.service;

import com.koreait.service_impl.dto.PostRequestDto;
import com.koreait.service_impl.dto.PostResponseDto;
import java.util.List;

public interface PostService {
    Long writePost(Long authorId, PostRequestDto postRequestDto);
    List<PostResponseDto> getPostList(int page, int size);
    int getTotalPostCount();
    PostResponseDto getPostDetail(Long postId);
    void updatePost(Long postId, Long authorId, PostRequestDto postRequestDto);
    void deletePost(Long postId, Long authorId);
}
