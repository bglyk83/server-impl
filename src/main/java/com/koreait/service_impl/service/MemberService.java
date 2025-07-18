package com.koreait.service_impl.service;

import com.koreait.service_impl.dto.LoginRequestDto;
import com.koreait.service_impl.dto.LoginResponseDto;
import com.koreait.service_impl.dto.SignupRequestDto;
import com.koreait.service_impl.entity.MemberEntity;

public interface MemberService {
    void signup(SignupRequestDto signupRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void updateMember(Long memberId, String email, String nickname);
    MemberEntity getMemberInfo(Long memberId);
    MemberEntity getMemberInfo(String username);
}
