package com.koreait.jpa.service;

import com.koreait.jpa.dto.LoginRequestDto;
import com.koreait.jpa.dto.LoginResponseDto;
import com.koreait.jpa.dto.SignupRequestDto;
import com.koreait.jpa.entity.MemberEntity;

public interface MemberService {
    void signup(SignupRequestDto signupRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void updateMember(Long memberId, String email, String nickname);
    MemberEntity getMemberInfo(Long memberId);
    MemberEntity getMemberInfo(String username);
}
