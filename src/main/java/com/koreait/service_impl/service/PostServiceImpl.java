package com.koreait.jpa.service;

import com.koreait.jpa.dto.PostRequestDto;
import com.koreait.jpa.dto.PostResponseDto;
import com.koreait.jpa.entity.PostEntity;
import com.koreait.jpa.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;

    @Transactional
    @Override
    public Long writePost(Long authorId, PostRequestDto postRequestDto) {
        PostEntity post = new PostEntity(
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                authorId
        );
        postMapper.insertPost(post);
        return post.getId();
    }

    @Override
    public List<PostResponseDto> getPostList(int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.findAllWithPaging(offset, size);
    }

    @Override
    public int getTotalPostCount() {
        return postMapper.countAll();
    }

    @Override
    public PostResponseDto getPostDetail(Long postId) {
        PostResponseDto post = postMapper.findById(postId);
        if (post == null) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }
        postMapper.incrementViewCount(postId);
        post.setViewCount(post.getViewCount() + 1);
        return post;
    }

    @Transactional
    @Override
    public void updatePost(Long postId, Long authorId, PostRequestDto postRequestDto) {
        Long postAuthorId = postMapper.findAuthorIdById(postId);
        if (postAuthorId == null) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }
        if (!postAuthorId.equals(authorId)) {
            throw new RuntimeException("게시글을 수정할 권한이 없습니다.");
        }
        PostEntity post = new PostEntity(
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                authorId
        );
        post.setId(postId);
        int result = postMapper.updatePost(post);
        if (result == 0) {
            throw new RuntimeException("게시글 수정에 실패했습니다.");
        }
    }

    @Transactional
    @Override
    public void deletePost(Long postId, Long authorId) {
        Long postAuthorId = postMapper.findAuthorIdById(postId);
        if (postAuthorId == null) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }
        if (!postAuthorId.equals(authorId)) {
            throw new RuntimeException("게시글을 삭제할 권한이 없습니다.");
        }
        int result = postMapper.deletePost(postId, authorId);
        if (result == 0) {
            throw new RuntimeException("게시글 삭제에 실패했습니다.");
        }
    }
} 