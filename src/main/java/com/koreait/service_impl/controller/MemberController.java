package com.koreait.service_impl.controller;

import com.koreait.service_impl.component.JwtTokenProvider;
import com.koreait.service_impl.dto.ApiResponseDto;
import com.koreait.service_impl.dto.LoginRequestDto;
import com.koreait.service_impl.dto.LoginResponseDto;
import com.koreait.service_impl.dto.SignupRequestDto;
import com.koreait.service_impl.entity.MemberEntity;
import com.koreait.service_impl.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<String>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            memberService.signup(signupRequestDto);
            return ResponseEntity.ok(ApiResponseDto.success("회원가입이 완료되었습니다.", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            LoginResponseDto loginResponse = memberService.login(loginRequestDto);
            return ResponseEntity.ok(ApiResponseDto.success("로그인이 완료되었습니다.", loginResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<MemberEntity>> getMemberInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtTokenProvider.getUsername(token);
            MemberEntity member = memberService.getMemberInfo(username);
            return ResponseEntity.ok(ApiResponseDto.success(member));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto<String>> updateMember(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String email,
            @RequestParam String nickname) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtTokenProvider.getUsername(token);
            MemberEntity member = memberService.getMemberInfo(username);
            memberService.updateMember(member.getId(), email, nickname);
            return ResponseEntity.ok(ApiResponseDto.success("회원 정보가 수정되었습니다.", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }
    }
}