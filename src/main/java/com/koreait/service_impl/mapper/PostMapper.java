package com.koreait.service_impl.mapper;

import com.koreait.service_impl.dto.PostResponseDto;
import com.koreait.service_impl.entity.PostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    
    // 게시글 작성
    int insertPost(PostEntity post);
    
    // 게시글 목록 조회 (페이징)
    List<PostResponseDto> findAllWithPaging(@Param("offset") int offset, @Param("limit") int limit);
    
    // 전체 게시글 수 조회
    int countAll();
    
    // 게시글 상세 조회
    PostResponseDto findById(@Param("id") Long id);
    
    // 조회수 증가
    int incrementViewCount(@Param("id") Long id);
    
    // 게시글 수정
    int updatePost(PostEntity post);
    
    // 게시글 삭제
    int deletePost(@Param("id") Long id, @Param("authorId") Long authorId);
    
    // 게시글 작성자 확인
    Long findAuthorIdById(@Param("id") Long id);
} 