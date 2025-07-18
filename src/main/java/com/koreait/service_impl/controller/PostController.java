package com.koreait.jpa.controller;

import com.koreait.jpa.component.JwtTokenProvider;
import com.koreait.jpa.dto.ApiResponseDto;
import com.koreait.jpa.dto.PostRequestDto;
import com.koreait.jpa.dto.PostResponseDto;
import com.koreait.jpa.entity.MemberEntity;
import com.koreait.jpa.service.MemberService;
import com.koreait.jpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ApiResponseDto<Long>> writePost(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody PostRequestDto postRequestDto) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtTokenProvider.getUsername(token);
            MemberEntity member = memberService.getMemberInfo(username);
            
            Long postId = postService.writePost(member.getId(), postRequestDto);
            return ResponseEntity.ok(ApiResponseDto.success("게시글이 작성되었습니다.", postId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<PostResponseDto>>> getPostList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<PostResponseDto> posts = postService.getPostList(page, size);
            int totalCount = postService.getTotalPostCount();
            
            return ResponseEntity.ok(ApiResponseDto.success(
                String.format("총 %d개의 게시글 중 %d페이지 조회 완료", totalCount, page), 
                posts
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<PostResponseDto>> getPostDetail(@PathVariable Long postId) {
        try {
            PostResponseDto post = postService.getPostDetail(postId);
            return ResponseEntity.ok(ApiResponseDto.success(post));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<String>> updatePost(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtTokenProvider.getUsername(token);
            MemberEntity member = memberService.getMemberInfo(username);
            
            postService.updatePost(postId, member.getId(), postRequestDto);
            return ResponseEntity.ok(ApiResponseDto.success("게시글이 수정되었습니다.", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto<String>> deletePost(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtTokenProvider.getUsername(token);
            MemberEntity member = memberService.getMemberInfo(username);
            
            postService.deletePost(postId, member.getId());
            return ResponseEntity.ok(ApiResponseDto.success("게시글이 삭제되었습니다.", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }
}


